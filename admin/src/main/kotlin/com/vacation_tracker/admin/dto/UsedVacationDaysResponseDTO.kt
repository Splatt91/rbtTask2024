package com.vacation_tracker.admin.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class UsedVacationDaysResponseDTO(
    @JsonFormat(pattern = "EEEE, MMMM d, yyyy")
    var startDate: LocalDate,
    @JsonFormat(pattern = "EEEE, MMMM d, yyyy")
    var endDate: LocalDate
)