package com.ajouin.noticescrapper.service.crawler.department

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class AdvancedMaterialsEngineeringNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://mse.ajou.ac.kr/mse/board/notice.do"
    override val type: Type = Type.첨단신소재공학과
    override val selector: String = Type1Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type1Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type1Utils.getIfTopFixedNotice(type, row)
    }
}