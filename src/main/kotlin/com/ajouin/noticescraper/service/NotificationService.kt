package com.ajouin.noticescraper.service

import com.slack.api.Slack
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class NotificationService (
    @Value("\${slack.webhook.url}")
    private val webHookUrl: String
) {
    fun notifyCompletion() {
        val slackClient = Slack.getInstance()
        slackClient.send(webHookUrl, "{\"text\" : \"공지사항 데이터 수집 완료\"}")
    }

    fun notifyError(error: String) {
        val slackClient = Slack.getInstance()
        slackClient.send(webHookUrl, "{\"text\" : \"$error\"}")
    }
}