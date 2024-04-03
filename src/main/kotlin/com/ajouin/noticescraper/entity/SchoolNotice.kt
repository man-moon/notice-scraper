package com.ajouin.noticescraper.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.Date

@Entity
class SchoolNotice (
    val title: String,
    var link: String,
    var isTopFixed: Boolean,
    val date: Date,
    var views: Long = 0,
    @Enumerated(EnumType.STRING) val noticeType: NoticeType,

    var fetchId: Long,

    @CreationTimestamp val createdAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun toString(): String {
        return "SchoolNotice(title='$title', link='$link', isTopFixed=$isTopFixed, date=$date, views=$views, noticeType=$noticeType, fetchId=$fetchId, createdAt=$createdAt, id=$id)"
    }
}