package com.ajouin.noticescrapper.service.crawler

import com.ajouin.noticescrapper.entity.LastIdPerType
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.logger
import com.ajouin.noticescrapper.repository.LastIdPerTypeRepository
import com.ajouin.noticescrapper.repository.SchoolNoticeRepository
import com.slack.api.Slack
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient

@Service
class SchoolNoticeCrawler (
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val lastIdPerTypeRepository: LastIdPerTypeRepository,
    private val strategies: List<NoticeCrawlerStrategy>,
    @Value("\${slack.webhook.url}")
    val webHookUrl: String
) {

    @Scheduled(fixedRate = 600000)  // 10분마다
    @Transactional
    fun crawl() {
        for (strategy in strategies) {
            crawlByStrategy(strategy)
        }

        val slackClient = Slack.getInstance()
        slackClient.send(webHookUrl, "{\"text\" : \"공지사항 데이터 수집 완료\"}")
    }

    fun crawlByStrategy(strategy: NoticeCrawlerStrategy) {
        val url = strategy.url
        val doc: Document
        try {
            doc = if(strategy.type == Type.간호대학) {
                val webClient = WebClient.create()
                val response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String::class.java)
                    .block() ?: throw Exception("간호대학 크롤링 실패")
                Jsoup.parse(response)
            } else {
                Jsoup.connect(url).get()
            }
        } catch (e: Exception) {
            logger.error { "공지사항 데이터 수집 실패" }
            val slackClient = Slack.getInstance()
            slackClient.send(webHookUrl, "{\"text\" : \"${strategy.type} 공지사항 데이터 수집 실패, ${e.message}\"}")
            return
        }
        val rows = doc.select(strategy.selector)
        val notices = mutableListOf<SchoolNotice>()

        val lastIdByType = lastIdPerTypeRepository.findByType(strategy.type)
            ?: lastIdPerTypeRepository.save(LastIdPerType(type = strategy.type, lastId = 0))

        var lastId = lastIdByType.lastId

        val oldTopFixedNotices = schoolNoticeRepository.findAllByTypeAndIsTopFixedIsTrue(strategy.type)
        val newTopFixedNotices = mutableListOf<SchoolNotice>()

        for (row in rows) {
            strategy.parseNotice(row, lastId)?.let {
                it.link = when (it.type) {
                    Type.소프트웨어학과0, Type.소프트웨어학과1 -> "http://software.ajou.ac.kr" + it.link
                    Type.의과대학 -> it.link
                    else -> url + it.link
                }

                notices.add(it)
            }
            strategy.getIfTopFixedNotice(row)?.let {
                it.link = when (it.type) {
                    Type.소프트웨어학과0, Type.소프트웨어학과1 -> "http://software.ajou.ac.kr" + it.link
                    Type.의과대학 -> it.link
                    else -> url + it.link
                }
                newTopFixedNotices.add(it)
            }
        }

        val newTopFixedNoticesIds = newTopFixedNotices.map { it.fetchId }.toSet()

        for (oldTopFixedNotice in oldTopFixedNotices) {
            if (oldTopFixedNotice.fetchId !in newTopFixedNoticesIds) {
                oldTopFixedNotice.isTopFixed = false
            }
        }
        //new 순회하면서 db에 있는지 확인.
        //있으면, isTopFixed를 true로 변경
        for (newTopFixedNotice in newTopFixedNotices) {
            val notice = schoolNoticeRepository.findByFetchIdAndType(newTopFixedNotice.fetchId, newTopFixedNotice.type)
            if(notice != null) {
                notice.isTopFixed = true
            }
            //db에 없고, notices 배열에도 존재하지 않는다면, 새롭게 추가한다.
            if (notice == null && newTopFixedNotice.fetchId !in notices.map { it.fetchId }) {
                schoolNoticeRepository.save(newTopFixedNotice)
            }
        }

        lastId = notices.maxOfOrNull { it.fetchId } ?: return

        lastIdByType.lastId = lastId

        schoolNoticeRepository.saveAll(notices)
    }

}