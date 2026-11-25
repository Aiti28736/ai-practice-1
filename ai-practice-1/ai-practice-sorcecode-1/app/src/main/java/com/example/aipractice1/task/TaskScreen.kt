package com.example.aipractice1.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.LocalDate
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var isDatePickerVisible by remember { mutableStateOf(false) }

    if (isDatePickerVisible) {
        val firstSelectableDateMillis = remember {
            LocalDate.now()
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()
        }
        val datePickerState = androidx.compose.material3.rememberDatePickerState(
            initialSelectedDateMillis = state.dueDateMillis,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= firstSelectableDateMillis
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let(viewModel::updateDueDate)
                        isDatePickerVisible = false
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("設定")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerVisible = false }) {
                    Text("キャンセル")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    TaskContent(
        state = state,
        canAdd = viewModel.canAdd(),
        onTitleChange = viewModel::updateTitle,
        onOpenDatePicker = { isDatePickerVisible = true },
        onAdd = viewModel::addTask
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TaskContent(
    state: TaskUiState,
    canAdd: Boolean,
    onTitleChange: (String) -> Unit,
    onOpenDatePicker: () -> Unit,
    onAdd: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("タスクを追加") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = state.title,
                onValueChange = onTitleChange,
                label = { Text("タスク名") },
                enabled = !state.isSubmitting,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "期限: ${state.dueDateText}")

            Button(
                onClick = onOpenDatePicker,
                enabled = !state.isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("期限を選択")
            }

            Button(
                onClick = onAdd,
                enabled = canAdd && !state.isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (state.isSubmitting) "追加中..." else "タスクを追加")
            }

            if (state.completed) {
                Text(text = "タスクを追加しました")
            }

            state.errorMessage?.let { message ->
                Text(text = message)
                TextButton(onClick = onAdd, enabled = !state.isSubmitting) {
                    Text("再試行")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPreview() {
    TaskContent(
        state = TaskUiState(
            title = "週次レポートを作成",
            dueDateMillis = 1_798_761_600_000L,
            dueDateText = "2027-01-15"
        ),
        canAdd = true,
        onTitleChange = {},
        onOpenDatePicker = {},
        onAdd = {}
    )
}
