package com.baskorp.timetracker.model

import kotlin.time.Duration

data class Timer (
    val id: String,
    val name: String,
    val timeLimit: Duration,
    val totalTimeSpent: Duration = Duration.ZERO,
    val isRunning: Boolean = false,
)