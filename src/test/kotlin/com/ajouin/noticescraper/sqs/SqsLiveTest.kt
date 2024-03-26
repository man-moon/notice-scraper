package com.ajouin.noticescraper.sqs

import com.ajouin.noticescraper.entity.NoticeType
import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.logger
import com.ajouin.noticescraper.repository.SchoolNoticeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class SqsLiveTest @Autowired constructor(
    private val sqsTemplate: SqsTemplate,
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val eventQueuesProperties: EventQueuesProperties,
    private val objectMapper: ObjectMapper,
) : BaseSqsIntegrationTest() {

    @Test
    fun `공지사항 페이로드를 전송하면 리시버가 받아야한다`() {
        // given
        val schoolNoticeCreatedEvent = SchoolNoticeCreatedEvent(
            title = "title1",
            link = "link1",
            isTopFixed = false,
            views = 0L,
            noticeType = NoticeType.소프트웨어학과1,
            fetchId = 1L,
            id = 1L,
        )
        val messagePayload = objectMapper.writeValueAsString(schoolNoticeCreatedEvent)

        // when
        sqsTemplate.send { to ->
            to.queue(eventQueuesProperties.contentRequestQueue)
                .payload(messagePayload)
        }
        logger.info { "Message sent with payload $schoolNoticeCreatedEvent" }

        //then
        await().atMost(Duration.ofSeconds(3))
            .until {
                schoolNoticeRepository.findByFetchIdAndNoticeType(
                    fetchId = schoolNoticeCreatedEvent.fetchId,
                    noticeType = schoolNoticeCreatedEvent.noticeType
                ) != null
            }
    }
}