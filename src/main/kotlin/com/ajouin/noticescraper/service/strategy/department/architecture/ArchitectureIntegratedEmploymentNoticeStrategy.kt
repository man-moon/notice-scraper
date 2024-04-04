package com.ajouin.noticescraper.service.strategy.department.architecture

import com.ajouin.noticescraper.parser.ParserFactory
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.error.exception.NoticeTypeNotFoundException
import org.springframework.stereotype.Component


@Component
class ArchitectureIntegratedEmploymentNoticeStrategy (
    parserFactory: ParserFactory
) : ScrapingStrategy {
    override val url: String = "https://arch.ajou.ac.kr/arch/board/job.do"
    final override val noticeType = NoticeType.건축학과1
    override val parser = parserFactory.getParser(noticeType)
        ?: throw NoticeTypeNotFoundException()
}