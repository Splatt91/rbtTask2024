package com.vacation_tracker.admin.repository

import com.vacation_tracker.admin.model.VacationDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VacationDaysRepository : JpaRepository<VacationDays, Long> {
    
}