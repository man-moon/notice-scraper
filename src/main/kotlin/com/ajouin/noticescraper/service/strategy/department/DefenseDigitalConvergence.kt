package com.ajouin.noticescraper.service.strategy.department

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class DefenseDigitalConvergence (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://mdc.ajou.ac.kr/mdc/administration/notice.do"
    final override val noticeType = NoticeType.국방디지털융합학과
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}
