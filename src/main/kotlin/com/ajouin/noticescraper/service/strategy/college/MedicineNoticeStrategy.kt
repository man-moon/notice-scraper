package com.ajouin.noticescraper.service.strategy.college

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class MedicineNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://www.ajoumc.or.kr/medicine/board/commBoardUVNoticeList.do"
    final override val noticeType = NoticeType.의과대학
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}