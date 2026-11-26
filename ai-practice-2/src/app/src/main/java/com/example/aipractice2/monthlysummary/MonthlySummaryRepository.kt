package com.example.aipractice2.monthlysummary

import java.io.IOException
import java.time.YearMonth

interface MonthlySummaryRepository {
    suspend fun fetchExpenses(month: YearMonth): List<MonthlyExpenseRecord>?
}

class MonthlySummaryRepositoryImpl(
    private val apiService: MonthlySummaryApiService = MonthlySummaryApiService()
) : MonthlySummaryRepository {
    override suspend fun fetchExpenses(month: YearMonth): List<MonthlyExpenseRecord>? {
        return try {
            apiService.fetchExpenses(month)
        } catch (_: IOException) {
            null
        }
    }
}
