package com.vacation_tracker.employee.dto

import java.time.LocalDate

data class UsedVacationDaysRequestDTO(
    var startDate: LocalDate,
    var endDate: LocalDate
)