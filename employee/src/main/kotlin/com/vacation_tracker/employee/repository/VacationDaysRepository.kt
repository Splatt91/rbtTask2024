package com.vacation_tracker.employee.repository

import com.vacation_tracker.employee.model.VacationDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VacationDaysRepository : JpaRepository<VacationDays, Long> {
    fun findByEmployeeEmail(email: String): List<VacationDays>
}