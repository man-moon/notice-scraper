package com.ajouin.noticescraper.service

import com.ajouin.noticescraper.logger
import com.ajouin.noticescraper.service.strategy.ScrapingStrategy
import com.ajouin.noticescraper.service.strategy.portal.GeneralNoticeStrategy
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScrapingScheduler(
    private val scrapingTask: ScrapingTask,
    private val notificationService: NotificationService,
    private val strategies: List<ScrapingStrategy>,
) {
    @Scheduled(fixedRate = 600000) // 10분마다
    fun execute() = runBlocking {
        logger.info { "Start Scraping" }
        strategies.map { strategy ->
            launch {
                try {
                    scrapingTask.execute(strategy)
                } catch (e: Exception) {
                    logger.error { "Fetch error occurred in ${strategy.noticeType}: ${e.message}" }
                    notificationService.notifyError("Fetch error occurred in ${strategy.noticeType}: ${e.message}")
                }
            }
        }.joinAll()
        notificationService.notifyCompletion()
        logger.info { "End Scraping" }
    }
}