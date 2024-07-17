package com.vacation_tracker.admin.service

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.vacation_tracker.admin.dto.EmployeeResponseDTO
import com.vacation_tracker.admin.dto.UsedVacationDaysResponseDTO
import com.vacation_tracker.admin.dto.VacationDaysResponseDTO
import com.vacation_tracker.admin.model.*
import com.vacation_tracker.admin.repository.EmployeeRepository
import com.vacation_tracker.admin.repository.UsedVacationDaysRepository
import com.vacation_tracker.admin.repository.VacationDaysRepository
import com.vacation_tracker.admin.util.createBufferedReaderForFile
import com.vacation_tracker.admin.util.parseFile
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class AdminServiceImpl : AdminService {

    private val logger: Log = LogFactory.getLog(this.javaClass)

    @Autowired
    private lateinit var csvMapper: CsvMapper
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var employeeRepository: EmployeeRepository
    @Autowired
    private lateinit var vacationDaysRepository: VacationDaysRepository
    @Autowired
    private lateinit var usedVacationDaysRepository: UsedVacationDaysRepository

    override fun ping(): String {
        return "Admin service ping!"
    }

    override fun importEmployeeProfiles(file: MultipartFile): ResponseEntity<List<EmployeeResponseDTO>> {
        val bufferedReader = createBufferedReaderForFile(file)
        bufferedReader.readLine()
        val employees = parseFile<Employee>(csvMapper, bufferedReader) {
            val item = Employee (
                it.id,
                it.email,
                passwordEncoder.encode(it.password)
            )
            item
        }
        val result = employeeRepository.saveAll(employees)
        return ResponseEntity(result.toListEmployeeResponseDto(), HttpStatus.OK)
    }

    override fun importTotalVacationDays(file: MultipartFile): ResponseEntity<List<VacationDaysResponseDTO>> {
        val bufferedReader = createBufferedReaderForFile(file)
        val year = bufferedReader.readLine()!!.split(",")[1].toLong()
        val totalVacationDays = parseFile<VacationDays>(csvMapper, bufferedReader) {
            val employee = employeeRepository.findByEmail(it.employee.email)
            val item = VacationDays(
                it.id,
                employee,
                it.totalDays,
                0,
                it.totalDays,
                year
            )
            item
        }
        val result = vacationDaysRepository.saveAll(totalVacationDays)
        return ResponseEntity(result.toListVacationDaysResponseDto(), HttpStatus.OK)
    }

    override fun importUsedVacationDays(file: MultipartFile): ResponseEntity<List<UsedVacationDaysResponseDTO>> {
        val bufferedReader = createBufferedReaderForFile(file)
        val usedVacationDays = parseFile<UsedVacationDays>(csvMapper, bufferedReader) {
            val employee = employeeRepository.findByEmail(it.employee.email)
            val item = UsedVacationDays(
                it.id,
                employee,
                it.startDate,
                it.endDate
            )
            item
        }
        val result = usedVacationDaysRepository.saveAll(usedVacationDays)
        return ResponseEntity(result.toListVacationDaysResponseDto(), HttpStatus.OK)
    }

}