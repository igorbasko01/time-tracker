package com.baskorp.timetracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform