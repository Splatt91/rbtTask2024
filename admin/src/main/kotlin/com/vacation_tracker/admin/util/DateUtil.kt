package com.vacation_tracker.admin.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun calculateYearlyDayDifferences(startDate: LocalDate, endDate: LocalDate): List<Pair<Int, Long>> {
    val results = mutableListOf<Pair<Int, Long>>()

    // Initialize the current date to the start date
    var currentStartDate = startDate

    if (currentStartDate.year < endDate.year) {
        // Calculate the end of the year date for the current year
        val endOfYear = LocalDate.of(currentStartDate.year, 12, 31)

        // Calculate the difference in days for the current year
        val daysInCurrentYear = ChronoUnit.DAYS.between(currentStartDate, endOfYear)
        results.add(Pair(currentStartDate.year, daysInCurrentYear))

        // Move to the start of the next year
        currentStartDate = endOfYear.plusDays(1)

        // Calculate the remaining days in the final year
        val daysInFinalYear = ChronoUnit.DAYS.between(currentStartDate, endDate.plusDays(1))
        results.add(Pair(currentStartDate.year, daysInFinalYear))
    } else {
        val daysInFinalYear = ChronoUnit.DAYS.between(startDate, endDate)
        results.add(Pair(currentStartDate.year, daysInFinalYear))
    }

    return results
}