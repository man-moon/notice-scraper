package com.ajouin.noticescrapper.service.crawler.department.architecture

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class ArchitectureIntegratedNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://arch.ajou.ac.kr/arch/board/board01.jsp"
    override val type: Type = Type.건축학과0
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type3Utils.getIfTopFixedNotice(type, row)
    }
}