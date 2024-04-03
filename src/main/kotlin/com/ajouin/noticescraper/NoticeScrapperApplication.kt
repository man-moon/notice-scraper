package com.ajouin.noticescraper

import com.ajouin.noticescraper.sqs.EventQueuesProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(EventQueuesProperties::class)
class NoticeScrapperApplication

fun main(args: Array<String>) {
	runApplication<NoticeScrapperApplication>(*args)
}
