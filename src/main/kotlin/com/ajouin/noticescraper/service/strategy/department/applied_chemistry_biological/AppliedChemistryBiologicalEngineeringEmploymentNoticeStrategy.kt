package com.ajouin.noticescraper.service.strategy.department.applied_chemistry_biological

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component

@Component
class AppliedChemistryBiologicalEngineeringEmploymentNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://chembio.ajou.ac.kr/chembio/admission/job.do"
    final override val noticeType = NoticeType.응용화학생명공학과1
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}