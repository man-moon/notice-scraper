package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.Utils.Companion.getPostId
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//옛날버전 + 서브공지(ex. 취업게시판)
object OldSubParser : Parser {
    override val selector = "#jwxe_main_content > div > div.list_wrap > table > tbody > tr"

    override fun parseRow(noticeType: NoticeType, row: Element): SchoolNotice {
        val title = row.select("td > a").text().trim()
        val link = row.select("td > a").attr("href")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(row.select("td:nth-child(5)").text().trim())

        val id = getPostId(link)

        return SchoolNotice(
            title = title,
            link = link,
            noticeType = noticeType,
            isTopFixed = false,
            date = date,
            fetchId = id,
        )
    }

    override fun parseNotice(noticeType: NoticeType, row: Element, lastId: Long): SchoolNotice? {
        val notice = parseRow(noticeType, row)
        return if (notice.fetchId > lastId || notice.isTopFixed) notice else null
    }
}