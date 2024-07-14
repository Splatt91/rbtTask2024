package com.vacation_tracker.admin.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AdminConfig {

    @Bean
    fun csvMapper(): CsvMapper {
        return CsvMapper.builder()
            .addModule(Jdk8Module())
            .addModule(JavaTimeModule())
            .build()
    }

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().findAndRegisterModules()
    }
}