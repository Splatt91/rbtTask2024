package com.vacation_tracker.admin.service

import com.vacation_tracker.admin.dto.EmployeeResponseDTO
import com.vacation_tracker.admin.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.admin.dto.VacationDaysResponseDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface AdminService {
    fun ping(): String
    fun importEmployeeProfiles(file: MultipartFile): ResponseEntity<List<EmployeeResponseDTO>>
    fun importTotalVacationDays(file: MultipartFile): ResponseEntity<List<VacationDaysResponseDTO>>
    fun importUsedVacationDays(file: MultipartFile): ResponseEntity<List<UsedVacationDaysResponseDTO>>
}