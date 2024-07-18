package com.vacation_tracker.employee.service

import com.vacation_tracker.employee.dto.UsedVacationDaysRequestDTO
import com.vacation_tracker.employee.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.employee.dto.VacationDaysResponseDTO
import org.springframework.http.ResponseEntity
import java.security.Principal

interface EmployeeService {
    fun ping(): String
    fun searchVacationDays(principal: Principal): ResponseEntity<Map<Long, List<VacationDaysResponseDTO>>>
    fun getUsedVacationDaysForGivenTimePeriod(principal: Principal, usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<List<UsedVacationDaysResponseDTO>>
    fun addUsedVacationDays(principal: Principal, usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<UsedVacationDaysResponseDTO>
}