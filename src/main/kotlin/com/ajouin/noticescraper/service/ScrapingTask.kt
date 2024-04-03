package com.ajouin.noticescraper.service

import com.ajouin.noticescraper.entity.LastIdPerNoticeType
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType.*
import com.ajouin.noticescraper.repository.LastIdPerNoticeTypeRepository
import com.ajouin.noticescraper.repository.SchoolNoticeRepository
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.sqs.ContentRequestEventPublisher
import com.ajouin.noticescraper.sqs.SchoolNoticeCreatedEvent
import com.ajouin.noticescraper.sqs.SchoolNoticeUpdateEvent
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.max

@Service
class ScrapingTask(
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val lastIdPerNoticeTypeRepository: LastIdPerNoticeTypeRepository,
    private val eventPublisher: ContentRequestEventPublisher,
) {
    @Transactional
    fun execute(strategy: ScrapingStrategy) {
        val doc = fetchDocument(strategy)
        val notices = parseDocument(doc, strategy)
        processTopFixedNotices(notices.filter { it.isTopFixed }, strategy)
        val oldLastId = updateLastId(notices, strategy)
        schoolNoticeRepository.saveAll(notices.filter { !it.isTopFixed })

        notices.filter {it.fetchId > oldLastId }
            .forEach {
                eventPublisher.publish(
                    SchoolNoticeCreatedEvent(
                        title = it.title,
                        link = it.link,
                        isTopFixed = it.isTopFixed,
                        views = it.views,
                        noticeType = it.noticeType,
                        fetchId = it.fetchId,
                        id = it.id
                    )
                )
            }
    }

    fun fetchDocument(strategy: ScrapingStrategy): Document {
        return try {
            strategy.fetch()
        } catch (e: Exception) {
            throw e
        }
    }

    fun parseDocument(doc: Document, strategy: ScrapingStrategy): MutableList<SchoolNotice> {
        return doc.select(strategy.parser.selector).mapNotNull { row ->
            strategy.parse(row, getLastId(strategy)).also { notice ->
                if (notice != null && notice.noticeType !in listOf(의과대학, 소프트웨어학과0, 소프트웨어학과1, 간호대학)) {
                    notice.link = strategy.url + notice.link
                }
            }
        }.toMutableList()
    }

    fun getLastId(strategy: ScrapingStrategy): Long {
        return lastIdPerNoticeTypeRepository.findByNoticeType(strategy.noticeType)?.lastId ?: 0L
    }

    fun processTopFixedNotices(newTopFixedNotices: List<SchoolNotice>, strategy: ScrapingStrategy) {
        val oldTopFixedNotices = schoolNoticeRepository.findAllByNoticeTypeAndIsTopFixedIsTrue(strategy.noticeType)

        // 기존 상단 고정 공지사항 중에서 새로 가져온 공지사항에 없는 것은 상단 고정 해제
        oldTopFixedNotices.forEach { old ->
            if (old.fetchId !in newTopFixedNotices.map { new -> new.fetchId }) {
                old.isTopFixed = false
                schoolNoticeRepository.save(old)
                eventPublisher.publish(
                    SchoolNoticeUpdateEvent(
                        fetchId = old.fetchId,
                        noticeType = old.noticeType,
                        isTopFixed = old.isTopFixed,
                    )
                )
            }
        }

        newTopFixedNotices.forEach {
            // 기존 공지사항이 상단 고정된 경우 업데이트
            schoolNoticeRepository.findByFetchIdAndNoticeType(it.fetchId, it.noticeType)
                ?.let { existingNotice ->
                    existingNotice.isTopFixed = true
                    schoolNoticeRepository.save(existingNotice)
                    eventPublisher.publish(
                        SchoolNoticeUpdateEvent(
                            fetchId = existingNotice.fetchId,
                            noticeType = existingNotice.noticeType,
                            isTopFixed = existingNotice.isTopFixed,
                        )
                    )
                }
                ?: run {
                    schoolNoticeRepository.save(it)
                }
        }
    }

    // 기존의 lastId를 반환
    fun updateLastId(notices: List<SchoolNotice>, strategy: ScrapingStrategy): Long {
        val newLastId = notices.maxOfOrNull { it.fetchId } ?: 0L

        val lastIdRecord = lastIdPerNoticeTypeRepository.findByNoticeType(strategy.noticeType)
            ?: LastIdPerNoticeType(noticeType = strategy.noticeType, lastId = 0L)

        val oldLastId = lastIdRecord.lastId

        lastIdRecord.lastId = max(newLastId, lastIdRecord.lastId)
        lastIdPerNoticeTypeRepository.save(lastIdRecord)

        return oldLastId
    }
}
