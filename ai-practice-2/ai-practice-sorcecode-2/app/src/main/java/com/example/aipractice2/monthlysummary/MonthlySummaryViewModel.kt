package com.example.aipractice2.monthlysummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth

data class MonthlySummaryUiState(
    val isLoading: Boolean = false,
    val summary: MonthlySummary? = null,
    val errorMessage: String? = null
)

class MonthlySummaryViewModel(
    private val useCase: MonthlySummaryUseCase = MonthlySummaryUseCaseImpl()
) : ViewModel() {
    private val _uiState = MutableStateFlow(MonthlySummaryUiState())
    val uiState: StateFlow<MonthlySummaryUiState> = _uiState.asStateFlow()

    fun load(month: YearMonth) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            summary = null,
            errorMessage = null
        )
        viewModelScope.launch {
            val result = useCase.getSummary(month)
            _uiState.value = if (result == null) {
                _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "サマリーを読み込めませんでした。再試行してください。"
                )
            } else {
                _uiState.value.copy(isLoading = false, summary = result)
            }
        }
    }
}
