package com.vacation_tracker.employee.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaCommandProducer {

    @Value("\${spring.kafka.topic.vacation-tracker.admin}")
    private lateinit var topic: String

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    fun send(message: Any) {
        kafkaTemplate.send(topic, message)
    }

}