package com.ajouin.noticescrapper.service.crawler

import com.ajouin.noticescrapper.entity.SchoolNotice
import com.ajouin.noticescrapper.entity.Type
import org.jsoup.nodes.Element

interface NoticeCrawlerStrategy {
    val url: String
    val selector: String
    val type: Type

    fun parseNotice(row: Element, lastId: Long): SchoolNotice?
    fun getIfTopFixedNotice(row: Element): SchoolNotice?
}
