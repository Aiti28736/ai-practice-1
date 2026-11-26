package com.example.aipractice2.expenseentry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExpenseEntryUiState(
    val amountText: String = "",
    val selectedCategory: String = "",
    val memo: String = "",
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class ExpenseEntryViewModel @Inject constructor(
    private val useCase: ExpenseEntryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExpenseEntryUiState())
    val uiState: StateFlow<ExpenseEntryUiState> = _uiState.asStateFlow()

    fun updateAmount(amountText: String) {
        if (amountText.any { it !in '0'..'9' }) return
        _uiState.value = _uiState.value.copy(
            amountText = amountText,
            isSaved = false,
            errorMessage = null
        )
    }

    fun selectCategory(category: String) {
        _uiState.value = _uiState.value.copy(
            selectedCategory = category,
            isSaved = false,
            errorMessage = null
        )
    }

    fun updateMemo(memo: String) {
        _uiState.value = _uiState.value.copy(
            memo = memo.take(ExpenseEntryConstraints.MAX_MEMO_LENGTH),
            isSaved = false,
            errorMessage = null
        )
    }

    fun canSave(): Boolean {
        val state = _uiState.value
        return useCase.isAmountValid(state.amountText) &&
            state.selectedCategory.isNotBlank() &&
            useCase.isMemoValid(state.memo) &&
            !state.isSaving
    }

    fun save() {
        val state = _uiState.value
        if (!canSave()) return

        _uiState.value = state.copy(isSaving = true, isSaved = false, errorMessage = null)
        viewModelScope.launch {
            val success = useCase.createExpense(
                amountText = state.amountText,
                category = state.selectedCategory,
                memo = state.memo
            )
            _uiState.value = if (success) {
                _uiState.value.copy(
                    amountText = "",
                    memo = "",
                    isSaving = false,
                    isSaved = true,
                    errorMessage = null
                )
            } else {
                _uiState.value.copy(
                    isSaving = false,
                    isSaved = false,
                    errorMessage = RETRY_MESSAGE
                )
            }
        }
    }

    private companion object {
        const val RETRY_MESSAGE = "支出を登録できませんでした。再試行してください。"
    }
}
