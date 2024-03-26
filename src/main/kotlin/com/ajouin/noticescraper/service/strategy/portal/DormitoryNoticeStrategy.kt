package com.ajouin.noticescraper.service.strategy.portal

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class DormitoryNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://www.ajou.ac.kr/dorm/notice/notice.do"
    final override val noticeType = NoticeType.생활관
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()


}