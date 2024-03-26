package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import org.jsoup.nodes.Element

interface Parser {
    val selector: String

    fun parseRow(noticeType: NoticeType, row: Element): SchoolNotice?
    fun parseNotice(noticeType: NoticeType, row: Element, lastId: Long): SchoolNotice?

}