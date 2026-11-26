package com.example.aipractice2.expenseentry

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExpenseEntryInputTest {
    private val useCase = ExpenseEntryUseCaseImpl(
        repository = object : ExpenseEntryRepository {
            override suspend fun createExpense(request: ExpenseEntryRequest): Boolean = true
        }
    )

    @Test
    fun amountValidationEnforcesSupportedRangeAndDigits() {
        assertTrue(useCase.isAmountValid("1"))
        assertTrue(useCase.isAmountValid("500000"))
        assertFalse(useCase.isAmountValid("0"))
        assertFalse(useCase.isAmountValid("500001"))
        assertFalse(useCase.isAmountValid("1,302"))
        assertFalse(useCase.isAmountValid("+1"))
    }

    @Test
    fun memoValidationEnforcesFortyCharacterLimit() {
        assertTrue(useCase.isMemoValid("a".repeat(40)))
        assertFalse(useCase.isMemoValid("a".repeat(41)))
    }

    @Test
    fun viewModelAcceptsOnlyHalfWidthDigitsAndCapsMemoInput() {
        val viewModel = ExpenseEntryViewModel(useCase)

        viewModel.updateAmount("1302")
        viewModel.updateAmount("1,302")
        viewModel.updateMemo("a".repeat(41))

        assertEquals("1302", viewModel.uiState.value.amountText)
        assertEquals("a".repeat(40), viewModel.uiState.value.memo)
    }

    @Test
    fun saveAvailabilityRejectsAmountAboveMaximum() {
        val viewModel = ExpenseEntryViewModel(useCase)
        viewModel.selectCategory("食費")
        viewModel.updateAmount("500000")
        assertTrue(viewModel.canSave())

        viewModel.updateAmount("500001")

        assertFalse(viewModel.canSave())
    }
}
