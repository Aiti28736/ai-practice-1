package com.example.aipractice1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.aipractice1.task.TaskScreen
import com.example.aipractice1.task.TaskViewModel
import com.example.aipractice1.ui.theme.AIPractice1Theme

class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIPractice1Theme {
                TaskScreen(viewModel = taskViewModel)
            }
        }
    }
}