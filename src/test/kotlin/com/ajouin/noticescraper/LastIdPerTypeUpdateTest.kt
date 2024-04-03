package com.ajouin.noticescraper

import com.ajouin.noticescraper.entity.LastIdPerNoticeType
import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.entity.NoticeType.*
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.repository.LastIdPerNoticeTypeRepository
import com.ajouin.noticescraper.service.ScrapingTask
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
class LastIdPerTypeUpdateTest @Autowired constructor(
    private val lastIdPerNoticeTypeRepository: LastIdPerNoticeTypeRepository,
    private val scrapingTask: ScrapingTask,
    private val generalNoticeStrategy: ScrapingStrategy,
) {

    @AfterEach
    fun clean() {
        lastIdPerNoticeTypeRepository.deleteAll()
    }

    @Test
    @DisplayName("lastId가 가장 마지막 fetchId로 갱신되어야 한다")
    fun test1() {
        val lastIdPerNoticeTypeData = LastIdPerNoticeType(
            noticeType = 일반공지,
            lastId = 3L
        )
        lastIdPerNoticeTypeRepository.save(lastIdPerNoticeTypeData)
        val notices = listOf(
            SchoolNotice(
                title = "title4",
                link = "link4",
                isTopFixed = false,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 4L,
            ),
        )

        scrapingTask.updateLastId(notices, generalNoticeStrategy)

        val lastIdPerNoticeType = lastIdPerNoticeTypeRepository.findByNoticeType(일반공지)
        val lastId = lastIdPerNoticeType?.lastId
        assertThat(lastId).isEqualTo(4L)
    }

    @Test
    @DisplayName("새로운 공지사항이 없는 경우, lastId는 그대로여야 한다")
    fun test2() {
        val lastIdPerNoticeTypeData = LastIdPerNoticeType(
            noticeType = 일반공지,
            lastId = 3L
        )
        lastIdPerNoticeTypeRepository.save(lastIdPerNoticeTypeData)
        val notices = listOf<SchoolNotice>()

        scrapingTask.updateLastId(notices, generalNoticeStrategy)

        val lastIdPerNoticeType = lastIdPerNoticeTypeRepository.findByNoticeType(일반공지)
        val lastId = lastIdPerNoticeType?.lastId
        assertThat(lastId).isEqualTo(3L)
    }

    @Test
    @DisplayName("새로운 공지사항과 기존 공지사항 모두 아무것도 없으면 lastId는 0이어야 한다")
    fun test3() {
        val notices = listOf<SchoolNotice>()

        scrapingTask.updateLastId(notices, generalNoticeStrategy)

        val lastIdPerNoticeType = lastIdPerNoticeTypeRepository.findByNoticeType(일반공지)
        val lastId = lastIdPerNoticeType?.lastId
        assertThat(lastId).isEqualTo(0L)
    }
}