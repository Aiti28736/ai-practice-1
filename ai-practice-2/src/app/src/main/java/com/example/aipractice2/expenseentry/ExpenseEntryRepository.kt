package com.example.aipractice2.expenseentry

import java.io.IOException
import javax.inject.Inject

interface ExpenseEntryRepository {
    suspend fun createExpense(request: ExpenseEntryRequest): Boolean
}

class ExpenseEntryRepositoryImpl @Inject constructor(
    private val apiService: ExpenseEntryApiService
) : ExpenseEntryRepository {
    override suspend fun createExpense(request: ExpenseEntryRequest): Boolean {
        return try {
            apiService.createExpense(request)
        } catch (_: IOException) {
            false
        }
    }
}
