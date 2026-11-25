package com.example.aipractice2.expenseentry

import java.time.LocalDate

interface ExpenseEntryUseCase {
    fun isAmountValid(amountText: String): Boolean
    fun isMemoValid(memo: String): Boolean
    suspend fun createExpense(amountText: String, category: String, memo: String): Boolean
}

class ExpenseEntryUseCaseImpl(
    private val repository: ExpenseEntryRepository = ExpenseEntryRepositoryImpl()
) : ExpenseEntryUseCase {
    override fun isAmountValid(amountText: String): Boolean {
        val amount = amountText.toIntOrNull() ?: return false
        return amount in 1..500_000
    }

    override fun isMemoValid(memo: String): Boolean = memo.length <= 40

    override suspend fun createExpense(
        amountText: String,
        category: String,
        memo: String
    ): Boolean {
        if (!isAmountValid(amountText) || category.isBlank() || !isMemoValid(memo)) {
            return false
        }

        return repository.createExpense(
            ExpenseEntryRequest(
                amount = amountText.toInt(),
                category = category,
                memo = memo,
                date = LocalDate.now()
            )
        )
    }
}
