package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//간호대학
//이걸로 변경 고려
//https://www.ajoumc.or.kr/nursing/board/commBoardNRNewsList.do
object NursingDepParser : Parser {
    override val selector = "#nt-con-1 a"

    override fun parseRow(noticeType: NoticeType, row: Element): SchoolNotice {
        val id = row.select("a").attr("href").substringAfter("no=").toLong()
        val title = row.select("a .ntp-inner strong").text()
        val link = row.select("a").attr("href")
        val modifiedLink = link.takeIf { it.startsWith("/nursing/") }?.replaceFirst("/nursing/", "") ?: link
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(row.select("a .ntp-inner .con span").text())

        return SchoolNotice(
            title = title,
            link = modifiedLink,
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