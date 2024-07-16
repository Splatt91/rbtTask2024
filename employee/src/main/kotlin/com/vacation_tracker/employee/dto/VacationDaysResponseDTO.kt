package com.vacation_tracker.employee.dto

data class VacationDaysResponseDTO (
    var totalDays: Long,
    var usedDays: Long,
    var remainingDays: Long,
    var year: Long
)