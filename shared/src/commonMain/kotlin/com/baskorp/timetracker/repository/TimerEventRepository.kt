package com.baskorp.timetracker.repository

import com.baskorp.timetracker.model.TimerEvent
import kotlinx.coroutines.flow.Flow

interface TimerEventRepository {
    suspend fun getEvents(timerId: String): Flow<List<TimerEvent>>
    suspend fun addEvent(event: TimerEvent)
    suspend fun updateEvent(event: TimerEvent)
    suspend fun deleteEventsForTimer(timerId: String)
}