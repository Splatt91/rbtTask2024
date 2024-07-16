package com.vacation_tracker.employee.repository

import com.vacation_tracker.employee.model.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByEmail(email:String): Employee
}