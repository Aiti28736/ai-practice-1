package com.example.aipractice2.monthlysummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

data class MonthlySummaryUiState(
    val selectedMonth: YearMonth = YearMonth.now(),
    val isLoading: Boolean = false,
    val summary: MonthlySummary? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class MonthlySummaryViewModel @Inject constructor(
    private val useCase: MonthlySummaryUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MonthlySummaryUiState())
    val uiState: StateFlow<MonthlySummaryUiState> = _uiState.asStateFlow()

    fun load() {
        loadMonth(_uiState.value.selectedMonth)
    }

    fun showPreviousMonth() {
        loadMonth(_uiState.value.selectedMonth.minusMonths(1))
    }

    fun showNextMonth() {
        loadMonth(_uiState.value.selectedMonth.plusMonths(1))
    }

    fun retry() {
        load()
    }

    private fun loadMonth(month: YearMonth) {
        _uiState.value = _uiState.value.copy(
            selectedMonth = month,
            isLoading = true,
            summary = null,
            errorMessage = null
        )
        viewModelScope.launch {
            val result = useCase.getSummary(month)
            if (_uiState.value.selectedMonth != month) return@launch
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
