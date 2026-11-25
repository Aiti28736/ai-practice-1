package com.example.aipractice1.task

import kotlinx.coroutines.delay

// タスク追加APIを呼び出す
class TaskApiService {
    suspend fun addTask(request: TaskRequest): Boolean {
        delay(100)
        return request.title.isNotBlank()
    }
}

data class TaskRequest(
    val title: String,
    val dueDateMillis: Long
)
