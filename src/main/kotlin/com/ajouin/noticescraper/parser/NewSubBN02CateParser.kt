package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.Utils
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//신버전 + 서브공지(ex. 취업정보) + bn-list-common02 + bn-common-cate + child(6) 버전
object NewSubBN02CateParser : Parser {

    override val selector = "#cms-content > div > div > div.bn-list-common02.type01.bn-common-cate > table > tbody > tr"

    override fun parseRow(noticeType: NoticeType, row: Element): SchoolNotice {
        val num = row.select("td.b-num-box").text()
        val title = row.select("td.b-td-left > div > a").text()
        val link = row.select("td.b-td-left > div > a").attr("href")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(row.select("td:nth-child(6)").text())

        val id = Utils.getPostId(link)

        return SchoolNotice(
            title = title,
            link = link,
            noticeType = noticeType,
            isTopFixed = num == "공지",
            date = date,
            fetchId = id,
        )
    }

    override fun parseNotice(noticeType: NoticeType, row: Element, lastId: Long): SchoolNotice? {
        val notice = parseRow(noticeType, row)
        return if (notice.fetchId > lastId || notice.isTopFixed) notice else null
    }
}