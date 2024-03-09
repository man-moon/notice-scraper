package com.ajouin.noticescrapper.service.crawler.department.chemistry

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type4Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ChemistrySeminarNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www2.ajou.ac.kr/chem/community/seminar.jsp"
    override val type: Type = Type.화학과1
    override val selector: String = Type4Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type4Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type4Utils.getIfTopFixedNotice(type, row)
    }
}