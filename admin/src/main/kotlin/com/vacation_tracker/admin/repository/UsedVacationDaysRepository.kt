package com.vacation_tracker.admin.repository

import com.vacation_tracker.admin.model.UsedVacationDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsedVacationDaysRepository : JpaRepository<UsedVacationDays, Long> {
    
}