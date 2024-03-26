package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.Utils
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//소웨 자체 공지
object SoftwareDepParser : Parser {
    override val selector = "#sub_contents > div > div.conbody > table:nth-child(2) > tbody > tr"

    override fun parseRow(noticeType: NoticeType, row: Element): SchoolNotice? {
        if (row.attr("height") != "45") {
            return null
        }
        var num: String = "0"

        val isTopFixed = row.select("td > img[src=\"/skin/bbs/basic_responsive_new/images/btn_notice.gif\"]").firstOrNull()
        if(isTopFixed != null) {
            num = "공지"
        }

        val titleAndLinkElement = row.select("td.responsive03 > a")
        val title = titleAndLinkElement.text().trim()
        val link = "http://software.ajou.ac.kr" + titleAndLinkElement.attr("href")

        if(title == "") return null

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateText = row.select("td.responsive03 > p.tablet_regist_date").text()
        val date = dateFormat.parse(dateText) ?: Date()

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
        val notice = parseRow(noticeType, row) ?: return null
        return if (notice.fetchId > lastId || notice.isTopFixed) notice else null
    }
}