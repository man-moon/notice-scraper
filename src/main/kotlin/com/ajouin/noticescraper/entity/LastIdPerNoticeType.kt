package com.ajouin.noticescraper.entity

import jakarta.persistence.*

@Entity
class LastIdPerNoticeType (

    var lastId: Long,

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    val noticeType: NoticeType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
)