package com.example.aipractice2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.aipractice2.expenseentry.ExpenseEntryViewModel
import com.example.aipractice2.monthlysummary.MonthlySummaryViewModel
import com.example.aipractice2.ui.theme.AIPractice2Theme

class MainActivity : ComponentActivity() {
    private val expenseEntryViewModel: ExpenseEntryViewModel by viewModels()
    private val monthlySummaryViewModel: MonthlySummaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIPractice2Theme {
                Practice2App(
                    expenseEntryViewModel = expenseEntryViewModel,
                    monthlySummaryViewModel = monthlySummaryViewModel
                )
            }
        }
    }
}