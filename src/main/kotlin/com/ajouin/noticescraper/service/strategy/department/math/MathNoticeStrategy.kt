package com.ajouin.noticescraper.service.strategy.department.math

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class MathNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://ajou.ac.kr/math/alram/notice.do"
    final override val noticeType = NoticeType.수학과0
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}