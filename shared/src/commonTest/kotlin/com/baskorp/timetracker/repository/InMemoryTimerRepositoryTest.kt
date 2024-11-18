package com.baskorp.timetracker.repository

import com.baskorp.timetracker.model.Timer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

class InMemoryTimerRepositoryTest {
    private lateinit var repository: TimerRepository

    @BeforeTest
    fun setup() {
        repository = InMemoryTimerRepository()
    }

    @Test
    fun `getTimers returns empty list when no timers exist`() = runTest {
        val response = repository.getTimers()
        assertTrue(response is RepositoryResult.Success)
        assertEquals(emptyList(), response.data.first())
    }

    @Test
    fun `createTimer adds a timer`() = runTest {
        val timer = Timer("1", "Timer 1", timeLimit = 10.minutes)
        repository.createTimer(timer)
        val response = repository.getTimers()
        assertTrue(response is RepositoryResult.Success)
        assertEquals(listOf(timer), response.data.first())
    }

    @Test
    fun `createTimer adds multiple timers`() = runTest {
        val timer1 = Timer("1", "Timer 1", timeLimit = 10.minutes)
        val timer2 = Timer("2", "Timer 2", timeLimit = 20.minutes)
        repository.createTimer(timer1)
        repository.createTimer(timer2)
        val response = repository.getTimers()
        assertTrue(response is RepositoryResult.Success)
        assertEquals(listOf(timer1, timer2), response.data.first())
    }

    @Test
    fun `createTimer fails when timer already exists`() = runTest {
        val timer = Timer("1", "Timer 1", timeLimit = 10.minutes)
        repository.createTimer(timer)
        val response = repository.createTimer(timer)
        assertTrue(response is RepositoryResult.AlreadyExists)
        assertEquals("1", response.id)
    }

    @Test
    fun `getTimer returns null when timer does not exist`() = runTest {
        val response = repository.getTimer("1")
        assertTrue(response is RepositoryResult.NotFound)
        assertEquals("1", response.id)
    }

    @Test
    fun `getTimer returns timer when timer exists`() = runTest {
        val timer = Timer("1", "Timer 1", timeLimit = 10.minutes)
        repository.createTimer(timer)
        val response = repository.getTimer("1")
        assertTrue(response is RepositoryResult.Success)
        assertEquals(timer, response.data)
    }

    @Test
    fun `getTimer returns timer when multiple timers exist`() = runTest {
        val timer1 = Timer("1", "Timer 1", timeLimit = 10.minutes)
        val timer2 = Timer("2", "Timer 2", timeLimit = 20.minutes)
        repository.createTimer(timer1)
        repository.createTimer(timer2)
        val response = repository.getTimer("2")
        assertTrue(response is RepositoryResult.Success)
        assertEquals(timer2, response.data)
    }

    @Test
    fun `updateTimer updates a timer`() = runTest {
        val timer = Timer("1", "Timer 1", timeLimit = 10.minutes)
        val createResponse = repository.createTimer(timer)
        val updatedTimer = timer.copy(name = "Updated Timer")
        val updateResponse = repository.updateTimer(updatedTimer)
        val retrieveResponse = repository.getTimer("1")
        assertTrue(createResponse is RepositoryResult.Success)
        assertTrue(updateResponse is RepositoryResult.Success)
        assertTrue(retrieveResponse is RepositoryResult.Success)
        assertEquals(updatedTimer, retrieveResponse.data)
    }

    @Test
    fun `updateTimer fails when timer does not exist`() = runTest {
        val timer = Timer("1", "Timer 1", timeLimit = 10.minutes)
        val response = repository.updateTimer(timer)
        assertTrue(response is RepositoryResult.NotFound)
        assertEquals("1", response.id)
    }

    @Test
    fun `deleteTimer removes a timer`() = runTest {
        val timer = Timer("1", "Timer 1", timeLimit = 10.minutes)
        repository.createTimer(timer)
        val deleteResponse = repository.deleteTimer("1")
        val retrieveResponse = repository.getTimer("1")

        assertTrue(deleteResponse is RepositoryResult.Success)
        assertEquals(timer, deleteResponse.data)
        assertTrue(retrieveResponse is RepositoryResult.NotFound)
        assertEquals("1", retrieveResponse.id)
    }
}