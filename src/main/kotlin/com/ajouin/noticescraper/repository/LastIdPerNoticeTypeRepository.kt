package com.ajouin.noticescraper.repository

import com.ajouin.noticescraper.entity.LastIdPerNoticeType
import com.ajouin.noticescraper.entity.NoticeType
import org.springframework.data.jpa.repository.JpaRepository

interface LastIdPerNoticeTypeRepository: JpaRepository<LastIdPerNoticeType, Long> {
    fun findByNoticeType(noticeType: NoticeType): LastIdPerNoticeType?

}