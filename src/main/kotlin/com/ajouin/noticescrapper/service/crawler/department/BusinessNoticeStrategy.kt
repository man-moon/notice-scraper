package com.ajouin.noticescrapper.service.crawler.department

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class BusinessNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://biz.ajou.ac.kr/abiz/board/notice.do"
    override val type: Type = Type.경영학과
    override val selector: String = Type1Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type1Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type1Utils.getIfTopFixedNotice(type, row)
    }
}