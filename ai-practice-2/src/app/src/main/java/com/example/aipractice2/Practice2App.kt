package com.example.aipractice2

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.aipractice2.expenseentry.ExpenseEntryScreen
import com.example.aipractice2.expenseentry.ExpenseEntryViewModel
import com.example.aipractice2.monthlysummary.MonthlySummaryScreen
import com.example.aipractice2.monthlysummary.MonthlySummaryViewModel

private enum class PracticeScreen {
    ExpenseEntry,
    MonthlySummary
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Practice2App(
    expenseEntryViewModel: ExpenseEntryViewModel,
    monthlySummaryViewModel: MonthlySummaryViewModel
) {
    var selectedScreen by rememberSaveable {
        mutableStateOf(PracticeScreen.ExpenseEntry)
    }
    val title = when (selectedScreen) {
        PracticeScreen.ExpenseEntry -> "支出登録"
        PracticeScreen.MonthlySummary -> "月次サマリー"
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(title) }) },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedScreen == PracticeScreen.ExpenseEntry,
                    onClick = { selectedScreen = PracticeScreen.ExpenseEntry },
                    icon = { Text("¥") },
                    label = { Text("支出登録") }
                )
                NavigationBarItem(
                    selected = selectedScreen == PracticeScreen.MonthlySummary,
                    onClick = { selectedScreen = PracticeScreen.MonthlySummary },
                    icon = { Text("月") },
                    label = { Text("月次サマリー") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedScreen) {
            PracticeScreen.ExpenseEntry -> ExpenseEntryScreen(
                viewModel = expenseEntryViewModel,
                modifier = Modifier.padding(innerPadding)
            )
            PracticeScreen.MonthlySummary -> MonthlySummaryScreen(
                viewModel = monthlySummaryViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
