package com.ajouin.noticescrapper.service.crawler.department.mechanical

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type4Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class MechanicalEmploymentNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://me.ajou.ac.kr/me/board/board06.jsp"
    override val type: Type = Type.기계공학과2
    override val selector: String = Type4Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type4Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type4Utils.getIfTopFixedNotice(type, row)
    }
}