package com.baskorp.timetracker.model

import kotlinx.datetime.Instant

data class TimerEvent (
    val id: String,
    val timerId: String,
    val startTime: Instant,
    val endTime: Instant?,
)