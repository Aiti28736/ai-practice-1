package com.example.aipractice2.monthlysummary

import kotlinx.coroutines.delay
import java.time.YearMonth

class MonthlySummaryApiService {
    suspend fun fetchExpenses(month: YearMonth): List<MonthlyExpenseRecord> {
        delay(180)
        if (month != YearMonth.now()) return emptyList()

        return listOf(
            MonthlyExpenseRecord(amount = 1280, category = "食費"),
            MonthlyExpenseRecord(amount = 860, category = "食費"),
            MonthlyExpenseRecord(amount = 620, category = "交通費"),
            MonthlyExpenseRecord(amount = 2480, category = "日用品"),
            MonthlyExpenseRecord(amount = 540, category = "その他")
        )
    }
}

data class MonthlyExpenseRecord(
    val amount: Int,
    val category: String
)
