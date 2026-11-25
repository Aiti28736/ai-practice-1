package com.example.aipractice2.monthlysummary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun MonthlySummaryScreen(
    viewModel: MonthlySummaryViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    // 表示対象の月を保持する
    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }

    LaunchedEffect(selectedMonth) {
        viewModel.load(selectedMonth)
    }

    MonthlySummaryContent(
        selectedMonth = selectedMonth,
        state = state,
        onPreviousMonth = { selectedMonth = selectedMonth.minusMonths(1) },
        onNextMonth = { selectedMonth = selectedMonth.plusMonths(1) },
        onRetry = { viewModel.load(selectedMonth) },
        modifier = modifier
    )
}

@Composable
private fun MonthlySummaryContent(
    selectedMonth: YearMonth,
    state: MonthlySummaryUiState,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = onPreviousMonth) {
                Text("前月")
            }
            Text(text = selectedMonth.format(DateTimeFormatter.ofPattern("yyyy年M月")))
            OutlinedButton(onClick = onNextMonth) {
                Text("翌月")
            }
        }

        when {
            state.isLoading -> CircularProgressIndicator()
            state.errorMessage != null -> {
                Text(text = state.errorMessage)
                TextButton(onClick = onRetry) {
                    Text("再試行")
                }
            }
            state.summary != null -> {
                Text(text = "合計支出")
                Text(text = "¥${state.summary.totalAmount}")
                HorizontalDivider()
                if (state.summary.categories.isEmpty()) {
                    Text(text = "この月の支出はありません")
                } else {
                    state.summary.categories.forEach { category ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = category.category)
                            Text(text = "¥${category.amount}")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthlySummaryPreview() {
    MonthlySummaryContent(
        selectedMonth = YearMonth.now(),
        state = MonthlySummaryUiState(
            summary = MonthlySummary(
                month = YearMonth.now(),
                totalAmount = 5780,
                categories = listOf(
                    CategorySpending("日用品", 2480),
                    CategorySpending("食費", 2140),
                    CategorySpending("交通費", 620),
                    CategorySpending("その他", 540)
                )
            )
        ),
        onPreviousMonth = {},
        onNextMonth = {},
        onRetry = {}
    )
}
