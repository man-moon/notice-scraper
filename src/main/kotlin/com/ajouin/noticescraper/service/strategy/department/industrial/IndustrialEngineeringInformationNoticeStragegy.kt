package com.ajouin.noticescraper.service.strategy.department.industrial

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class IndustrialEngineeringInformationNoticeStragegy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://www.ajou.ac.kr/ie/academic/shared.do"
    final override val noticeType = NoticeType.산업공학과1
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}