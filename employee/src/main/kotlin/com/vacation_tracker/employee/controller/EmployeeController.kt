package com.vacation_tracker.employee.controller

import com.vacation_tracker.employee.dto.UsedVacationDaysRequestDTO
import com.vacation_tracker.employee.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.employee.dto.VacationDaysResponseDTO
import com.vacation_tracker.employee.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/employee")
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @GetMapping("/ping")
    fun ping(): String {
        return employeeService.ping()
    }

    @GetMapping("vacation-days")
    @ResponseBody
    fun searchVacationDays(principal: Principal) : ResponseEntity<Map<Long, List<VacationDaysResponseDTO>>> {
        return employeeService.searchVacationDays(principal)
    }

    @PostMapping("used-vacation-days/fetch")
    @ResponseBody
    fun getUsedVacationDaysForGivenTimePeriod(@RequestBody usedVacationDays: UsedVacationDaysRequestDTO)
    : ResponseEntity<List<UsedVacationDaysResponseDTO>> {
        return employeeService.getUsedVacationDaysForGivenTimePeriod(usedVacationDays)
    }

    @PostMapping("used-vacation-days/add")
    @ResponseBody
    fun addUsedVacationDays(@RequestBody usedVacationDays: UsedVacationDaysRequestDTO): ResponseEntity<UsedVacationDaysResponseDTO> {
        return employeeService.addUsedVacationDays(usedVacationDays)
    }
}