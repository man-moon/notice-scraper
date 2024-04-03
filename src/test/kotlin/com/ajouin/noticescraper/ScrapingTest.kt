package com.ajouin.noticescraper

import com.ajouin.noticescraper.entity.LastIdPerNoticeType
import com.ajouin.noticescraper.entity.NoticeType.*
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.repository.LastIdPerNoticeTypeRepository
import com.ajouin.noticescraper.repository.SchoolNoticeRepository
import com.ajouin.noticescraper.service.ScrapingTask
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.Date


// <종합 테스트>
// processTopFixedNotices가 잘 동작하는지 확인

@SpringBootTest
@ActiveProfiles("test")
class ScrapingTest @Autowired constructor(
    private val scrapingTask: ScrapingTask,
    private val generalNoticeStrategy: ScrapingStrategy,
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val lastIdPerNoticeTypeRepository: LastIdPerNoticeTypeRepository,
) {
    @BeforeEach
    fun setup() {
        val notices = listOf(
            SchoolNotice(
                title = "title1",
                link = "link1",
                isTopFixed = false,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 1,
            ),
            SchoolNotice(
                title = "title2",
                link = "link2",
                isTopFixed = false,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 2,
            ),
            SchoolNotice(
                title = "title3",
                link = "link3",
                isTopFixed = true,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 3,
            )
        )
        val lastIdPerNoticeType = LastIdPerNoticeType(
            noticeType = 일반공지,
            lastId = 3
        )
        schoolNoticeRepository.saveAll(notices)
        lastIdPerNoticeTypeRepository.save(lastIdPerNoticeType)

    }

    @AfterEach
    fun clean() {
        schoolNoticeRepository.deleteAll()
        lastIdPerNoticeTypeRepository.deleteAll()
    }

    @Test
    @DisplayName("새로운 글이면서 상단에 고정이 되어있는 경우")
    fun test1() {
        // given
        val newNotices = listOf(
            SchoolNotice(
                title = "title3",
                link = "link3",
                isTopFixed = true,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 3,
            ),
            SchoolNotice(
                title = "title4",
                link = "link4",
                isTopFixed = true,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 4,
            ),
        )

        // when
        scrapingTask.processTopFixedNotices(newNotices, generalNoticeStrategy)
        val topFixedNotices =
            schoolNoticeRepository.findAllByNoticeTypeAndIsTopFixedIsTrue(일반공지)

        // then
        assertThat(topFixedNotices.size).isEqualTo(2)
    }

    @Test
    @DisplayName("이미 존재하던 게시글이 상단에 고정된 경우")
    fun test2() {
        // given
        val newNotices = listOf(
            SchoolNotice(
                title = "title2",
                link = "link2",
                isTopFixed = true,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 2,
            ),
            SchoolNotice(
                title = "title3",
                link = "link3",
                isTopFixed = true,
                date = Date(),
                noticeType = 일반공지,
                fetchId = 3,
            ),
        )
        // when
        scrapingTask.processTopFixedNotices(newNotices, generalNoticeStrategy)
        val topFixedNotices =
            schoolNoticeRepository.findAllByNoticeTypeAndIsTopFixedIsTrue(일반공지)

        // then
        assertThat(topFixedNotices.size).isEqualTo(2)
    }

    @Test
    @DisplayName("기존에 상단에 고정된 게시글이 사라진 경우")
    fun test3() {
        // given
        val newNotices = listOf<SchoolNotice>()
        // when
        scrapingTask.processTopFixedNotices(newNotices, generalNoticeStrategy)
        val topFixedNotices =
            schoolNoticeRepository.findAllByNoticeTypeAndIsTopFixedIsTrue(일반공지)

        // then
        assertThat(topFixedNotices.size).isEqualTo(0)
    }
}