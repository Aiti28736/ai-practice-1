package com.example.aipractice1.task

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

interface TaskUseCase {
    suspend fun add(title: String, dueDateMillis: Long): Boolean
    fun isValidTitle(title: String): Boolean
    fun isValidDueDate(dueDateMillis: Long?): Boolean
}

class TaskUseCaseImpl(
    // 本来はDIを使用
    private val repository: TaskRepository = TaskRepositoryImpl()
): TaskUseCase {
    override suspend fun add(title: String, dueDateMillis: Long): Boolean {
        return repository.addTask(title.trim(), dueDateMillis)
    }

    override fun isValidTitle(title: String): Boolean = title.trim().length in 2..30

    override fun isValidDueDate(dueDateMillis: Long?): Boolean {
        if (dueDateMillis == null) return false

        val selectedDate = Instant.ofEpochMilli(dueDateMillis)
            .atZone(ZoneOffset.UTC)
            .toLocalDate()
        return !selectedDate.isBefore(LocalDate.now())
    }
}
