package com.ajouin.noticescraper.service.strategy.department.mechanical

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class MechanicalEngineeringNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://me.ajou.ac.kr/me/board/under-notice.do"
    final override val noticeType = NoticeType.기계공학과0
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}