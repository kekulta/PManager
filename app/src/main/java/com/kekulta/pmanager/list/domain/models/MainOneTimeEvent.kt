package com.kekulta.pmanager.list.domain.models

sealed class MainOneTimeEvent {
    data class SnackBar(val message: String) : MainOneTimeEvent()
    data object RequireAuth : MainOneTimeEvent()
}