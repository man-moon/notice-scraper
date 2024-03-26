package com.ajouin.noticescraper.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.Date

@Entity
class SchoolNotice (
    val title: String,
    var link: String,
    var isTopFixed: Boolean,
    val date: Date,
    var views: Long = 0,
    @Enumerated(EnumType.STRING)
    val noticeType: NoticeType,

    var fetchId: Long,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun toString(): String {
        return "SchoolNotice(title='$title', link='$link', isTopFixed=$isTopFixed, date=$date, views=$views, noticeType=$noticeType, fetchId=$fetchId, createdAt=$createdAt, id=$id)"
    }
}