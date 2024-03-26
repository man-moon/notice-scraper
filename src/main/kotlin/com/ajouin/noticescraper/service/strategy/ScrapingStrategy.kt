package com.ajouin.noticescraper.service.strategy

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.parser.Parser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

interface ScrapingStrategy {
    val url: String
    val noticeType: NoticeType
    val parser: Parser

    fun fetch(): Document {
        return Jsoup.connect(url).get()
    }

    fun parse(row: Element, lastId: Long): SchoolNotice? {
        return parser.parseNotice(noticeType, row, lastId)
    }

}
