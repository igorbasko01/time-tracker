package com.baskorp.timetracker.repository

import com.baskorp.timetracker.model.Timer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class InMemoryTimerRepository : TimerRepository {
    private val timersFlow = MutableStateFlow<List<Timer>>(emptyList())
    override suspend fun getTimers(): RepositoryResult<Flow<List<Timer>>> {
        return RepositoryResult.Success(timersFlow)
    }

    override suspend fun getTimer(timerId: String): RepositoryResult<Timer> {
        val timer = timersFlow.value.find { it.id == timerId }
        return if (timer != null) {
            RepositoryResult.Success(timer)
        } else {
            RepositoryResult.NotFound(timerId)
        }
    }

    override suspend fun createTimer(timer: Timer): RepositoryResult<Timer> {
        val alreadyExists = timersFlow.value.any { it.id == timer.id }
        return if (alreadyExists) {
            RepositoryResult.AlreadyExists(timer.id)
        } else {
            timersFlow.value += timer
            RepositoryResult.Success(timer)
        }
    }

    override suspend fun updateTimer(timer: Timer): RepositoryResult<Timer> {
        val index = timersFlow.value.indexOfFirst { it.id == timer.id }
        return if (index == -1) {
            RepositoryResult.NotFound(timer.id)
        } else {
            timersFlow.value = timersFlow.value.toMutableList().apply { set(index, timer) }
            RepositoryResult.Success(timer)
        }
    }

    override suspend fun deleteTimer(timerId: String): RepositoryResult<Timer> {
        val index = timersFlow.value.indexOfFirst { it.id == timerId }
        return if (index == -1) {
            RepositoryResult.NotFound(timerId)
        } else {
            val timer = timersFlow.value[index]
            timersFlow.value = timersFlow.value.toMutableList().apply { removeAt(index) }
            RepositoryResult.Success(timer)
        }
    }

}