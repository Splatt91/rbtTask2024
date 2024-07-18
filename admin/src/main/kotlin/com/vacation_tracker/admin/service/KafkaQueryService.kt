package com.vacation_tracker.admin.service

import com.vacation_tracker.admin.model.Employee
import com.vacation_tracker.admin.model.UsedVacationDays
import com.vacation_tracker.admin.repository.EmployeeRepository
import com.vacation_tracker.admin.repository.UsedVacationDaysRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(topics = ["\${spring.kafka.topic.vacation-tracker.admin}"],
    groupId = "\${spring.kafka.consumer.group-id}")
class KafkaQueryService {

    @Autowired
    private lateinit var usedVacationDaysRepository: UsedVacationDaysRepository

    @KafkaHandler
    fun handleUsedVacationDays(usedVacationDays: UsedVacationDays) {
        usedVacationDaysRepository.save(usedVacationDays)
    }
}