package com.vacation_tracker.employee.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.vacation_tracker.employee.dto.VacationDaysResponseDTO
import jakarta.persistence.*

@Entity
@Table(name = "VacationDays")
data class VacationDays(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_email", referencedColumnName = "email")
    @JsonProperty("Employee")
    val employee: Employee,
    @JsonProperty("Total vacation days")
    var totalDays: Long,
    @JsonProperty("Used vacation days")
    var usedDays: Long,
    @JsonProperty("Remaining vacation days")
    var remainingDays: Long,
    @JsonProperty("Year")
    var year: Long
)

fun VacationDays.toVacationDaysResponseDto() = VacationDaysResponseDTO(
    totalDays = totalDays,
    usedDays = usedDays,
    remainingDays = remainingDays,
    year = year
)

fun List<VacationDays>.toListVacationDaysResponseDto(): List<VacationDaysResponseDTO> {
    val result = ArrayList<VacationDaysResponseDTO>()
    iterator().forEach {
        result.add(it.toVacationDaysResponseDto())
    }
    return result
}
