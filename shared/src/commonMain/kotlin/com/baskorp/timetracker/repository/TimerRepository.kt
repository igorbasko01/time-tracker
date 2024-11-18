package com.baskorp.timetracker.repository

import com.baskorp.timetracker.model.Timer
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun getTimers(): RepositoryResult<Flow<List<Timer>>>
    suspend fun getTimer(timerId: String): RepositoryResult<Timer>
    suspend fun createTimer(timer: Timer): RepositoryResult<Timer>
    suspend fun updateTimer(timer: Timer): RepositoryResult<Timer>
    suspend fun deleteTimer(timerId: String): RepositoryResult<Timer>
}