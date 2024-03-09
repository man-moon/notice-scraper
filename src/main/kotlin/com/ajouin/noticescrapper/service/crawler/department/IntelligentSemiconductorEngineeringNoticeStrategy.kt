package com.ajouin.noticescrapper.service.crawler.department

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type8Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class IntelligentSemiconductorEngineeringNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://aisemi.ajou.ac.kr/aisemi/board/notice.do"
    override val type: Type = Type.지능형반도체공학과
    override val selector: String = Type8Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type8Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type8Utils.getIfTopFixedNotice(type, row)
    }
}