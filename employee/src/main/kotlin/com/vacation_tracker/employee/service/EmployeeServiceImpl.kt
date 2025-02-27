package com.vacation_tracker.employee.service


import com.vacation_tracker.employee.dto.UsedVacationDaysRequestDTO
import com.vacation_tracker.employee.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.employee.dto.VacationDaysResponseDTO
import com.vacation_tracker.employee.model.UsedVacationDays
import com.vacation_tracker.employee.model.toListVacationDaysResponseDto
import com.vacation_tracker.employee.model.toUsedVacationDaysResponseDto
import com.vacation_tracker.employee.repository.EmployeeRepository
import com.vacation_tracker.employee.repository.UsedVacationDaysRepository
import com.vacation_tracker.employee.repository.VacationDaysRepository
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.security.Principal


@Service
class EmployeeServiceImpl : EmployeeService {

    private val logger: Log = LogFactory.getLog(this.javaClass)

    @Autowired
    private lateinit var kafkaCommandProducer: KafkaCommandProducer
    @Autowired
    private lateinit var vacationDaysRepository: VacationDaysRepository
    @Autowired
    private lateinit var usedVacationDaysRepository: UsedVacationDaysRepository
    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    override fun ping(): String {
        return "Employee service ping!"
    }

    override fun searchVacationDays(principal: Principal): ResponseEntity<Map<Long, List<VacationDaysResponseDTO>>> {
        logger.info(principal.name)
        val vacationDays = vacationDaysRepository.findByEmployeeEmail(principal.name)
        val result = vacationDays.toListVacationDaysResponseDto().groupBy { it.year }
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun getUsedVacationDaysForGivenTimePeriod(principal: Principal, usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<List<UsedVacationDaysResponseDTO>> {
        val usedVacationDays =
            usedVacationDaysRepository.findByEmployeeEmailAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                principal.name,
                usedVacationDaysRequestDto.startDate,
                usedVacationDaysRequestDto.endDate
            )
        val result = usedVacationDays.toListVacationDaysResponseDto()
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addUsedVacationDays(principal: Principal, usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<UsedVacationDaysResponseDTO> {
        val employee = employeeRepository.findByEmail(principal.name)
        val usedVacationDays =
            UsedVacationDays(null, employee, usedVacationDaysRequestDto.startDate, usedVacationDaysRequestDto.endDate)
        val result = usedVacationDaysRepository.save(usedVacationDays)
        kafkaCommandProducer.send(result)
        return ResponseEntity(result.toUsedVacationDaysResponseDto(), HttpStatus.OK)
    }

}