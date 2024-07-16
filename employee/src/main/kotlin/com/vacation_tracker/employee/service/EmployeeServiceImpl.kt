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

@Service
class EmployeeServiceImpl : EmployeeService {

    private val logger: Log = LogFactory.getLog(this.javaClass)

    @Autowired
    private lateinit var vacationDaysRepository: VacationDaysRepository

    @Autowired
    private lateinit var usedVacationDaysRepository: UsedVacationDaysRepository

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    override fun ping(): String {
        return "Employee service ping!"
    }


    override fun searchVacationDays(): ResponseEntity<Map<Long, List<VacationDaysResponseDTO>>> {
        val vacationDays = vacationDaysRepository.findByEmployeeEmail("user1@rbt.rs")
        val result = vacationDays.toListVacationDaysResponseDto().groupBy { it.year }
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun getUsedVacationDaysForGivenTimePeriod(usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<List<UsedVacationDaysResponseDTO>> {
        val usedVacationDays =
            usedVacationDaysRepository.findByEmployeeEmailAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                "user13@rbt.rs",
                usedVacationDaysRequestDto.startDate,
                usedVacationDaysRequestDto.endDate
            )
        val result = usedVacationDays.toListVacationDaysResponseDto()
        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addUsedVacationDays(usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<UsedVacationDaysResponseDTO> {
        val employee = employeeRepository.findByEmail("user13@rbt.rs")
        val usedVacationDays =
            UsedVacationDays(null, employee, usedVacationDaysRequestDto.startDate, usedVacationDaysRequestDto.endDate)
        val result = usedVacationDaysRepository.save(usedVacationDays).toUsedVacationDaysResponseDto()
        return ResponseEntity(result, HttpStatus.OK)
    }

}