package com.example.aipractice2.expenseentry

import java.io.IOException

interface ExpenseEntryRepository {
    suspend fun createExpense(request: ExpenseEntryRequest): Boolean
}

class ExpenseEntryRepositoryImpl(
    private val apiService: ExpenseEntryApiService = ExpenseEntryApiService()
) : ExpenseEntryRepository {
    override suspend fun createExpense(request: ExpenseEntryRequest): Boolean {
        return try {
            apiService.createExpense(request)
        } catch (_: IOException) {
            false
        }
    }
}
