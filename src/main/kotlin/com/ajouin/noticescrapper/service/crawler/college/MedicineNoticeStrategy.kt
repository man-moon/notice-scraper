package com.ajouin.noticescrapper.service.crawler.college

import com.ajouin.noticescrapper.service.crawler.NoticeCrawlerStrategy
import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import com.ajouin.noticescrapper.service.utils.notice.Type10Utils
import com.ajouin.noticescrapper.service.utils.notice.Type1Utils
import org.jsoup.nodes.Element
import org.springframework.stereotype.Component

@Component
class MedicineNoticeStrategy : NoticeCrawlerStrategy {
    override val url: String = "https://www.ajoumc.or.kr/medicine/board/commBoardUVNoticeList.do"
    override val type: Type = Type.의과대학
    override val selector: String = Type10Utils.SELECTOR

    override fun parseNotice(row: Element, lastId: Long): SchoolNotice? {
        return Type10Utils.parseNotice(type, row, lastId)
    }
    override fun getIfTopFixedNotice(row: Element): SchoolNotice? {
        return Type10Utils.getIfTopFixedNotice(type, row)
    }
}