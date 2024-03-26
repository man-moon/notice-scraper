package com.ajouin.noticescraper.sqs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "events.queues")
class EventQueuesProperties (
    val contentRequestQueue: String,
)