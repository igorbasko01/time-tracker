package com.baskorp.timetracker.repository

import com.baskorp.timetracker.model.Timer
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun getTimers(): Flow<List<Timer>>
    suspend fun getTimer(timerId: String): Timer?
    suspend fun createTimer(timer: Timer)
    suspend fun updateTimer(timer: Timer)
    suspend fun deleteTimer(timerId: String)
}