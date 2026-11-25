package com.example.aipractice1.task

import java.io.IOException

interface TaskRepository {
    suspend fun addTask(title: String, dueDateMillis: Long): Boolean
}

class TaskRepositoryImpl(
    private val apiService: TaskApiService = TaskApiService() // 本来はDIを使用
) : TaskRepository {
    override suspend fun addTask(title: String, dueDateMillis: Long): Boolean {
        return try {
            apiService.addTask(TaskRequest(title, dueDateMillis))
        } catch (_: IOException) {
            false
        }
    }
}
