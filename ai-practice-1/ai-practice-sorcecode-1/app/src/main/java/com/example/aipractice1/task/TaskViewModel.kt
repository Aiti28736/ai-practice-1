package com.example.aipractice1.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class TaskUiState(
    val title: String = "",
    val dueDateMillis: Long? = null,
    val dueDateText: String = "未選択",
    val isSubmitting: Boolean = false,
    val completed: Boolean = false,
    val errorMessage: String? = null
)

class TaskViewModel(
    private val useCase: TaskUseCase = TaskUseCaseImpl() // 本来はDIを使用
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title,
            completed = false,
            errorMessage = null
        )
    }

    fun updateDueDate(dueDateMillis: Long) {
        _uiState.value = _uiState.value.copy(
            dueDateMillis = dueDateMillis,
            dueDateText = formatDueDate(dueDateMillis),
            completed = false,
            errorMessage = null
        )
    }

    fun canAdd(): Boolean {
        val state = _uiState.value
        return !state.isSubmitting &&
                useCase.isValidTitle(state.title) &&
                useCase.isValidDueDate(state.dueDateMillis)
    }

    fun addTask() {
        val state = _uiState.value
        val dueDateMillis = state.dueDateMillis ?: return
        if (!canAdd()) return

        _uiState.value = state.copy(
            isSubmitting = true,
            completed = false,
            errorMessage = null
        )

        viewModelScope.launch {
            val success = useCase.add(state.title, dueDateMillis)
            _uiState.value = _uiState.value.copy(
                isSubmitting = false,
                completed = success,
                errorMessage = if (success) null else RETRY_MESSAGE
            )
        }
    }

    private fun formatDueDate(dueDateMillis: Long): String {
        return Instant.ofEpochMilli(dueDateMillis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    private companion object {
        const val RETRY_MESSAGE = "タスクを追加できませんでした。再試行してください。"
    }
}
