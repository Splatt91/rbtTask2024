package com.vacation_tracker.employee.config.security

import com.vacation_tracker.employee.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class EmployeeDetailsService: UserDetailsService {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val employee = employeeRepository.findByEmail(email)

        return User(
            employee.email,
            employee.password,
            emptyList()
        )
    }


}