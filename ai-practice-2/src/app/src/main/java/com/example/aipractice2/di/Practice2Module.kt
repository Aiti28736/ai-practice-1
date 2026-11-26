package com.example.aipractice2.di

import com.example.aipractice2.expenseentry.ExpenseEntryRepository
import com.example.aipractice2.expenseentry.ExpenseEntryRepositoryImpl
import com.example.aipractice2.expenseentry.ExpenseEntryUseCase
import com.example.aipractice2.expenseentry.ExpenseEntryUseCaseImpl
import com.example.aipractice2.monthlysummary.MonthlySummaryRepository
import com.example.aipractice2.monthlysummary.MonthlySummaryRepositoryImpl
import com.example.aipractice2.monthlysummary.MonthlySummaryUseCase
import com.example.aipractice2.monthlysummary.MonthlySummaryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Practice2Module {
    @Binds
    abstract fun bindExpenseEntryRepository(
        implementation: ExpenseEntryRepositoryImpl
    ): ExpenseEntryRepository

    @Binds
    abstract fun bindExpenseEntryUseCase(
        implementation: ExpenseEntryUseCaseImpl
    ): ExpenseEntryUseCase

    @Binds
    abstract fun bindMonthlySummaryRepository(
        implementation: MonthlySummaryRepositoryImpl
    ): MonthlySummaryRepository

    @Binds
    abstract fun bindMonthlySummaryUseCase(
        implementation: MonthlySummaryUseCaseImpl
    ): MonthlySummaryUseCase
}
