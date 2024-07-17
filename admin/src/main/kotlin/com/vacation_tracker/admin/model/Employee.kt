package com.vacation_tracker.admin.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.vacation_tracker.admin.dto.EmployeeResponseDTO
import jakarta.persistence.*

@Entity
@Table(name = "Employee")
data class Employee (@Id
                     @GeneratedValue(strategy = GenerationType.IDENTITY)
                     @JsonProperty("Employee Id")
                     val id: Long? = null,
                     @JsonProperty("Employee Email")
                     val email: String,
                     @JsonProperty("Employee Password")
                     val password: String? = null
) {
    constructor(email: String): this (null, email, null)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (email != other.email) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        return result
    }
}

fun Employee.toEmployeeResponseDto() = EmployeeResponseDTO (
    email = email
)

fun List<Employee>.toListEmployeeResponseDto(): List<EmployeeResponseDTO> {
    val result = ArrayList<EmployeeResponseDTO>()
    iterator().forEach {
        result.add(it.toEmployeeResponseDto())
    }
    return result
}