package com.ajouin.noticescrapper.service.crawler.department.digital_media

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import com.ajouin.noticescrapper.service.utils.notice.Type3Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class DigitalMediaNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://media.ajou.ac.kr/media/board/board01.jsp"
    override val type: Type = Type.디지털미디어학과0
    override val selector: String = Type3Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type3Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type3Utils.getIfTopFixedNotice(type, row)
    }
}