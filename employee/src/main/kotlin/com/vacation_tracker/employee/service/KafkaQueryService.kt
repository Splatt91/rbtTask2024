package com.vacation_tracker.employee.service

import com.vacation_tracker.employee.model.Employee
import com.vacation_tracker.employee.model.UsedVacationDays
import com.vacation_tracker.employee.model.VacationDays
import com.vacation_tracker.employee.repository.EmployeeRepository
import com.vacation_tracker.employee.repository.UsedVacationDaysRepository
import com.vacation_tracker.employee.repository.VacationDaysRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(topics = ["\${spring.kafka.topic.vacation-tracker.employee}"],
    groupId = "\${spring.kafka.consumer.group-id}")
class KafkaQueryService {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository
    @Autowired
    private lateinit var usedVacationDaysRepository: UsedVacationDaysRepository
    @Autowired
    private lateinit var vacationDaysRepository: VacationDaysRepository

    @KafkaHandler
    fun handleEmployee(employee: Employee) {
        employeeRepository.save(employee)
    }

    @KafkaHandler
    fun handleUsedVacationDays(usedVacationDays: UsedVacationDays) {
        usedVacationDaysRepository.save(usedVacationDays)
    }

    @KafkaHandler
    fun handleVacationDays(vacationDays: VacationDays) {
        vacationDaysRepository.save(vacationDays)
    }
}