package com.example.aipractice2.expenseentry

import kotlinx.coroutines.delay
import java.time.LocalDate

class ExpenseEntryApiService {
    suspend fun createExpense(request: ExpenseEntryRequest): Boolean {
        delay(150)
        return request.amount > 0 && request.category.isNotBlank()
    }
}

data class ExpenseEntryRequest(
    val amount: Int,
    val category: String,
    val memo: String,
    val date: LocalDate
)
