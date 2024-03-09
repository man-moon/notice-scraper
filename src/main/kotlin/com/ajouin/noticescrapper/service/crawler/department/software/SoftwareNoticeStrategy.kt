package com.ajouin.noticescrapper.service.crawler.department.software

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type2Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class SoftwareNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "http://software.ajou.ac.kr/bbs/board.php?tbl=notice"
    override val type: Type = Type.소프트웨어학과0
    override val selector: String = Type2Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type2Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type2Utils.getIfTopFixedNotice(type, row)
    }
}