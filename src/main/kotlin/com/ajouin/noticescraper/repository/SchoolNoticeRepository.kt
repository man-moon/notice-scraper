package com.ajouin.noticescraper.repository

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SchoolNoticeRepository: JpaRepository<SchoolNotice, Long> {
    fun findTopByOrderByIdDesc(): SchoolNotice?

    fun findAllByNoticeTypeAndIsTopFixedIsTrue(noticeType: NoticeType): List<SchoolNotice>
    fun findByFetchIdAndNoticeType(fetchId: Long, noticeType: NoticeType): SchoolNotice?
}