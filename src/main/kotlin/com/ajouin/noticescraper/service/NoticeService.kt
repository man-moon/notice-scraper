package com.ajouin.noticescraper.service

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.exception.NoticeNotFoundException
import com.ajouin.noticescraper.repository.SchoolNoticeQuerydslRepository
import com.ajouin.noticescraper.repository.SchoolNoticeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NoticeService (
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val schoolNoticeQuerydslRepository: SchoolNoticeQuerydslRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(NoticeService::class.java)
    @Transactional
    fun getNotice(notices: List<String>): List<SchoolNotice> {
        val result = mutableListOf<SchoolNotice>()

        //상단 고정 공지는 항상 모두 가져오고, 상단 고정 공지가 아닌 공지사항을 fetchId가 큰 순으로 10개씩 가져오기(페이징 처리)

        for (notice in notices) {
            val noticeTypes: List<NoticeType> = findEnumValuesContaining(notice)
            if(noticeTypes.contains(NoticeType.소프트웨어학과0)) {
                schoolNoticeQuerydslRepository.findNoticesByPagingForSoftwareDep(0, 20, noticeTypes, true)
            } else {
                schoolNoticeQuerydslRepository.findNoticesByPaging(0, 20, noticeTypes, true)
            }.let { result.addAll(it) }
        }
//        result.sortByDescending { it.fetchId }

        return result
    }

    @Transactional
    fun getNotice(type: String, offset: Long): List<SchoolNotice> {
        val noticeTypes: List<NoticeType> = findEnumValuesContaining(type)
        return if(noticeTypes.contains(NoticeType.소프트웨어학과0)) {
            schoolNoticeQuerydslRepository.findNoticesByPagingForSoftwareDep(offset, 20, noticeTypes, false)
        } else {
            schoolNoticeQuerydslRepository.findNoticesByPaging(offset, 20, noticeTypes, false)
        }
    }


    @Transactional
    fun postViews(noticeId: Long) {
        val notice = schoolNoticeRepository.findByIdOrNull(noticeId) ?: throw NoticeNotFoundException()
        notice.views += 1
    }

    fun findEnumValuesContaining(substring: String): List<NoticeType> {
        return NoticeType.entries.filter { it.name.contains(substring) }
    }
}