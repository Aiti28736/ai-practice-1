package com.example.aipractice2.expenseentry

import java.time.LocalDate
import javax.inject.Inject

interface ExpenseEntryUseCase {
    fun isAmountValid(amountText: String): Boolean
    fun isMemoValid(memo: String): Boolean
    suspend fun createExpense(amountText: String, category: String, memo: String): Boolean
}

class ExpenseEntryUseCaseImpl @Inject constructor(
    private val repository: ExpenseEntryRepository
) : ExpenseEntryUseCase {
    override fun isAmountValid(amountText: String): Boolean {
        if (amountText.isEmpty() || amountText.any { it !in '0'..'9' }) return false
        val amount = amountText.toIntOrNull() ?: return false
        return amount in ExpenseEntryConstraints.MIN_AMOUNT..ExpenseEntryConstraints.MAX_AMOUNT
    }

    override fun isMemoValid(memo: String): Boolean =
        memo.length <= ExpenseEntryConstraints.MAX_MEMO_LENGTH

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
