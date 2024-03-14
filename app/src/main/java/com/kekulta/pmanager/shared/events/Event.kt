package com.kekulta.pmanager.shared.events


sealed class Event() {
    data class ShowSnackBar(val message: String) : Event()
    data object RequireAuth : Event()
}

enum class EventStatus {
    EXECUTED(),
    NOT_EXECUTED()
}