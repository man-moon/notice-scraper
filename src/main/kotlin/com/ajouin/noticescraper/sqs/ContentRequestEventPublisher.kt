package com.ajouin.noticescraper.sqs

import com.ajouin.noticescraper.logger
import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.operations.SqsTemplate
import org.springframework.stereotype.Component

@Component
class ContentRequestEventPublisher(
    private val sqsTemplate: SqsTemplate,
    private val objectMapper: ObjectMapper,
    private val eventQueuesProperties: EventQueuesProperties,
) {

    fun publish(event: SchoolNoticeCreatedEvent) {
        val messagePayload = objectMapper.writeValueAsString(event)
        sqsTemplate.send { to ->
            to.queue(eventQueuesProperties.contentRequestQueue)
                .payload(messagePayload)
        }

        logger.info { "Message sent with payload $event" }
    }

    fun publish(event: SchoolNoticeUpdateEvent) {
        val messagePayload = objectMapper.writeValueAsString(event)
        sqsTemplate.send { to ->
            to.queue(eventQueuesProperties.noticeUpdateRequestQueue)
                .payload(messagePayload)
        }

        logger.info { "Message sent with payload $event" }
    }

}