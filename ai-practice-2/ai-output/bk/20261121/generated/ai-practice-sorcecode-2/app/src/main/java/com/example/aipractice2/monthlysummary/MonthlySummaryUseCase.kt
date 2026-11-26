package com.example.aipractice2.monthlysummary

import java.time.YearMonth

interface MonthlySummaryUseCase {
    suspend fun getSummary(month: YearMonth): MonthlySummary?
}

class MonthlySummaryUseCaseImpl(
    private val repository: MonthlySummaryRepository = MonthlySummaryRepositoryImpl()
) : MonthlySummaryUseCase {
    override suspend fun getSummary(month: YearMonth): MonthlySummary? {
        val expenses = repository.fetchExpenses(month) ?: return null
        val categories = expenses
            .groupBy { it.category }
            .map { (category, records) ->
                CategorySpending(
                    category = category,
                    amount = records.sumOf { it.amount }
                )
            }
            .sortedByDescending { it.amount }

        return MonthlySummary(
            month = month,
            totalAmount = expenses.sumOf { it.amount },
            categories = categories
        )
    }
}

data class MonthlySummary(
    val month: YearMonth,
    val totalAmount: Int,
    val categories: List<CategorySpending>
)

data class CategorySpending(
    val category: String,
    val amount: Int
)
