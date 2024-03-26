package com.ajouin.noticescraper.service.strategy.college

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class BusinessCollegeNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url = "https://biz.ajou.ac.kr/biz/community/notice.do"
    final override val noticeType = NoticeType.경영대학
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}