package com.ajouin.noticescraper.sqs

import com.ajouin.noticescraper.entity.NoticeType
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SchoolNoticeUpdateEvent @JsonCreator constructor(
    @JsonProperty("fetchId") val fetchId: Long,
    @JsonProperty("noticeType") val noticeType: NoticeType,
    @JsonProperty("isTopFixed") @get:JsonProperty("isTopFixed") val isTopFixed: Boolean,
)
