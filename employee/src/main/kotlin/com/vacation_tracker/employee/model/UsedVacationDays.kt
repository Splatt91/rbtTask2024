package com.vacation_tracker.employee.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.vacation_tracker.employee.dto.UsedVacationDaysRequestDTO
import com.vacation_tracker.employee.dto.UsedVacationDaysResponseDTO
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "UsedVacationDays")
data class UsedVacationDays(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_email", referencedColumnName = "email")
    @JsonProperty("Employee")
    val employee: Employee,
    @JsonProperty("Vacation start date")
    @JsonFormat(pattern = "EEEE, MMMM d, yyyy")
    var startDate: LocalDate,
    @JsonProperty("Vacation end date")
    @JsonFormat(pattern = "EEEE, MMMM d, yyyy")
    var endDate: LocalDate
)

fun UsedVacationDays.toUsedVacationDaysResponseDto() = UsedVacationDaysResponseDTO(
    startDate = startDate,
    endDate = endDate
)

fun List<UsedVacationDays>.toListVacationDaysResponseDto(): List<UsedVacationDaysResponseDTO> {
    val result = ArrayList<UsedVacationDaysResponseDTO>()
    iterator().forEach {
        result.add(it.toUsedVacationDaysResponseDto())
    }
    return result
}