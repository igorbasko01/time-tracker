package com.baskorp.timetracker.repository

sealed class RepositoryResult<out T> {
    data class Success<T>(val data: T) : RepositoryResult<T>()
    data class AlreadyExists(val id: String) : RepositoryResult<Nothing>()
    data class NotFound(val id: String): RepositoryResult<Nothing>()
    data class Error(val exception: Exception): RepositoryResult<Nothing>()
}