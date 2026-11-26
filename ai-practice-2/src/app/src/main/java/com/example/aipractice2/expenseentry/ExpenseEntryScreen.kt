package com.example.aipractice2.expenseentry

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val expenseCategories = listOf("食費", "交通費", "日用品", "その他")

@Composable
fun ExpenseEntryScreen(
    viewModel: ExpenseEntryViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ExpenseEntryContent(
        state = state,
        canSave = viewModel.canSave(),
        onAmountChange = viewModel::updateAmount,
        onCategorySelect = viewModel::selectCategory,
        onMemoChange = viewModel::updateMemo,
        onSave = viewModel::save,
        modifier = modifier
    )
}

@Composable
private fun ExpenseEntryContent(
    state: ExpenseEntryUiState,
    canSave: Boolean,
    onAmountChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onMemoChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = state.amountText,
            onValueChange = onAmountChange,
            label = { Text("金額") },
            suffix = { Text("円") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "1円〜500,000円")

        Text(text = "カテゴリ")
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            expenseCategories.forEach { category ->
                FilterChip(
                    selected = state.selectedCategory == category,
                    onClick = { onCategorySelect(category) },
                    label = { Text(category) },
                    enabled = !state.isSaving
                )
            }
        }

        OutlinedTextField(
            value = state.memo,
            onValueChange = onMemoChange,
            label = { Text("メモ（任意）") },
            supportingText = {
                Text("${state.memo.length}/${ExpenseEntryConstraints.MAX_MEMO_LENGTH}")
            },
            singleLine = true,
            enabled = !state.isSaving,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSave,
            enabled = canSave && !state.isSaving,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (state.isSaving) "登録中..." else "支出を登録")
        }

        if (state.isSaved) {
            Text(text = "支出を登録しました")
        }
        state.errorMessage?.let { message ->
            Text(text = message)
            TextButton(onClick = onSave, enabled = !state.isSaving) {
                Text("再試行")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExpenseEntryPreview() {
    ExpenseEntryContent(
        state = ExpenseEntryUiState(
            amountText = "1280",
            selectedCategory = "食費",
            memo = "昼食"
        ),
        canSave = true,
        onAmountChange = {},
        onCategorySelect = {},
        onMemoChange = {},
        onSave = {}
    )
}
