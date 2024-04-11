package com.comunidadedevspace.taskbeats.core.domain.network

interface ConnectivityObserver {
    fun isConnected(): Boolean
}