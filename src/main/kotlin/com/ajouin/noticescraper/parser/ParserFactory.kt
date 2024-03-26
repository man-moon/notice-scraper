package com.ajouin.noticescraper.parser

import com.ajouin.noticescraper.entity.NoticeType
import org.springframework.stereotype.Component

@Component
class ParserFactory(
    private val parsers: Map<NoticeType, Parser>
) {
    fun getParser(noticeType: NoticeType): Parser? = parsers[noticeType]
}