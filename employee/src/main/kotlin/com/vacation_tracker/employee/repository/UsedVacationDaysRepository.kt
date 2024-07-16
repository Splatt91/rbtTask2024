package com.vacation_tracker.employee.repository

import com.vacation_tracker.employee.model.UsedVacationDays
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface UsedVacationDaysRepository : JpaRepository<UsedVacationDays, Long> {

    fun findByEmployeeEmailAndStartDateGreaterThanEqualAndEndDateLessThanEqual(employeeEmail:String,
                                                                               fromDate:LocalDate,
                                                                               toDate: LocalDate)
    : List<UsedVacationDays>
}