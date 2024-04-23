package com.comunidadedevspace.taskbeats.core.domain.util

sealed interface DataError: Error {
    enum class Network: DataError {
        TOP_NEWS_NOTY_FOUND_EXCEPTION,
        TOP_NEWS_NOTY_FOUND_HTTP_EXCEPTION,
        ALL_NEWS_NOTY_FOUND_EXCEPTION,
        ALL_NEWS_NOTY_FOUND_HTTP_EXCEPTION
    }
}