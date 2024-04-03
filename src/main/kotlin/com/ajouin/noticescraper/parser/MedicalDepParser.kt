package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import org.jsoup.nodes.Element
import java.text.SimpleDateFormat
import java.util.*

//의과대학
object MedicalDepParser : Parser {
    override val selector = "#contents > article > section > div > div.tb_w > table > tbody > tr"

    override fun parseRow(noticeType: NoticeType, row: Element): SchoolNotice {
        val num = row.select("td.td_num").text()
        val title = row.select("td.td_qna > a > span").text()
        val id = row.select("td.td_qna > a").attr("href").substringAfter("no: ").substringBefore(" } )").toLong()
        val link = "https://www.ajoumc.or.kr/medicine/board/commBoardUVNoticeView.do?no=$id"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(row.select("td.td_date").text())

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
        return if (notice.fetchId > lastId || notice.isTopFixed)
            notice
        else
            null
    }
}