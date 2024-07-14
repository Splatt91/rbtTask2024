package com.vacation_tracker.admin.controller

import com.vacation_tracker.admin.dto.EmployeeResponseDTO
import com.vacation_tracker.admin.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.admin.dto.VacationDaysResponseDTO
import com.vacation_tracker.admin.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admin")
class AdminController {

    @Autowired
    private lateinit var adminService: AdminService

    @GetMapping("/ping")
    fun ping(): String {
        return adminService.ping()
    }

    @PostMapping("/import/employee-profiles")
    @ResponseBody
    fun importEmployeeProfiles(@RequestParam("file") file : MultipartFile): ResponseEntity<List<EmployeeResponseDTO>> {
        return adminService.importEmployeeProfiles(file)
    }

    @PostMapping("/import/total-vacation-days")
    @ResponseBody
    fun importTotalVacationDays(@RequestParam("file") file : MultipartFile): ResponseEntity<List<VacationDaysResponseDTO>> {
        return adminService.importTotalVacationDays(file)
    }

    @PostMapping("/import/used-vacation-days")
    @ResponseBody
    fun importUsedVacationDays(@RequestParam("file") file : MultipartFile) : ResponseEntity<List<UsedVacationDaysResponseDTO>> {
        return adminService.importUsedVacationDays(file)
    }
}