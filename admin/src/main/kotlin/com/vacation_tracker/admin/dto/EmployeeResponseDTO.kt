package com.vacation_tracker.admin.dto

data class EmployeeResponseDTO (
    val email: String,
    val password: String? = null
)