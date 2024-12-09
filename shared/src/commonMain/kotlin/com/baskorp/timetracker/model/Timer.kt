package com.baskorp.timetracker.model

import kotlin.time.Duration

/**
 * Represents a timer that can be started and stopped.
 * @param id The unique identifier of the timer.
 * @param name The name of the timer.
 * @param timeLimit The time limit of the timer.
 * @param totalTimeSpent The total time spent on the timer.
 * @param isRunning Whether the timer is currently running.
 */
data class Timer (
    val id: String,
    val name: String,
    val timeLimit: Duration,
    val totalTimeSpent: Duration = Duration.ZERO,
    val isRunning: Boolean = false,
)