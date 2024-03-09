package com.ajouin.noticescrapper.service.crawler.department.chemical_engineering

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type6Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ChemicalEngineeringEmploymentNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajou.ac.kr/che/board/employment.do"
    override val type: Type = Type.화학공학과1
    override val selector: String = Type6Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type6Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type6Utils.getIfTopFixedNotice(type, row)
    }
}