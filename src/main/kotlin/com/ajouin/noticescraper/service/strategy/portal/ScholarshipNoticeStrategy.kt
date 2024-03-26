package com.ajouin.noticescraper.service.strategy.portal

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class ScholarshipNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://www.ajou.ac.kr/kr/ajou/notice_scholarship.do"
    final override val noticeType = NoticeType.장학공지
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()


}