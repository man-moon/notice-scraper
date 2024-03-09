package com.ajouin.noticescrapper.service.crawler.department.industrial

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type5Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class IndustrialEngineeringInformationNoticeStragegy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajou.ac.kr/ie/academic/shared.do"
    override val type: Type = Type.산업공학과1
    override val selector: String = Type5Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type5Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type5Utils.getIfTopFixedNotice(type, row)
    }
}