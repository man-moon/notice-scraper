package com.ajouin.noticescraper.sqs

import com.ajouin.noticescraper.entity.SchoolNotice
import com.ajouin.noticescraper.logger
import com.ajouin.noticescraper.repository.SchoolNoticeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class SchoolNoticeEventListeners (
    private val schoolNoticeRepository: SchoolNoticeRepository,
    private val objectMapper: ObjectMapper,
) {
//    @SqsListener("\${events.queues.content-request-queue}")
    fun receiveStringMessage(message: String) {
        logger.info { "Received message: $message" }

        val schoolNoticeCreatedEvent = objectMapper.readValue(
            message,
            SchoolNoticeCreatedEvent::class.java
        )
        val schoolNotice = SchoolNotice(
            title = schoolNoticeCreatedEvent.title,
            link = schoolNoticeCreatedEvent.link,
            isTopFixed = schoolNoticeCreatedEvent.isTopFixed,
            views = schoolNoticeCreatedEvent.views,
            date = Date(),
            noticeType = schoolNoticeCreatedEvent.noticeType,
            fetchId = schoolNoticeCreatedEvent.fetchId,
            id = schoolNoticeCreatedEvent.id,
        )

        schoolNoticeRepository.save(schoolNotice)
    }
}