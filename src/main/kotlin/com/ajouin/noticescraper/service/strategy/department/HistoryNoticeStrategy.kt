package com.ajouin.noticescraper.service.strategy.department

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class HistoryNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://history.ajou.ac.kr/history/admission/notice.do"
    final override val noticeType = NoticeType.사학과
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}
