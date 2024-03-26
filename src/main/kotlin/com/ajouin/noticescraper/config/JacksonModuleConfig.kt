package com.ajouin.noticescraper.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonModuleConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}