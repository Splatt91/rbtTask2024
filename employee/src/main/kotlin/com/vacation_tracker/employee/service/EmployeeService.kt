package com.vacation_tracker.employee.service

import com.vacation_tracker.employee.dto.UsedVacationDaysRequestDTO
import com.vacation_tracker.employee.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.employee.dto.VacationDaysResponseDTO
import org.springframework.http.ResponseEntity

interface EmployeeService {
    fun ping(): String
    fun searchVacationDays(): ResponseEntity<Map<Long, List<VacationDaysResponseDTO>>>
    fun getUsedVacationDaysForGivenTimePeriod(usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<List<UsedVacationDaysResponseDTO>>
    fun addUsedVacationDays(usedVacationDaysRequestDto: UsedVacationDaysRequestDTO): ResponseEntity<UsedVacationDaysResponseDTO>
}