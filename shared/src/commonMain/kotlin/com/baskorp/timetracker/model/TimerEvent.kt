package com.baskorp.timetracker.model

import kotlinx.datetime.Instant

/**
 * Represents a timer event. A timer event is a period of time during which a timer was running.
 * @param id The unique identifier of the timer event.
 * @param timerId The unique identifier of the timer that the event belongs to.
 * @param startTime The start time of the event.
 * @param endTime The end time of the event.
 */
data class TimerEvent (
    val id: String,
    val timerId: String,
    val startTime: Instant,
    val endTime: Instant?,
)