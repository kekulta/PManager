package com.kekulta.pmanager.list.domain.viewmodels

import com.kekulta.pmanager.list.domain.models.MainOneTimeEvent
import com.kekulta.pmanager.list.presentation.ui.AuthManager
import com.kekulta.pmanager.shared.AbstractCoroutineViewModel
import com.kekulta.pmanager.shared.events.Event
import com.kekulta.pmanager.shared.events.EventDispatcher
import com.kekulta.pmanager.shared.events.EventExecutor
import com.kekulta.pmanager.shared.events.EventStatus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class MainViewModel(
    private val authManager: AuthManager,
    dispatcher: EventDispatcher,
) : AbstractCoroutineViewModel(), EventExecutor, EventDispatcher by dispatcher {
    private val messageBus = Channel<MainOneTimeEvent>()

    init {
        attach(this)
    }

    override fun execute(event: Event): EventStatus {
        return when (event) {
            is Event.ShowSnackBar -> {
                sendMainEvent(MainOneTimeEvent.SnackBar(event.message))
                EventStatus.EXECUTED
            }

            is Event.RequireAuth -> {
                sendMainEvent(MainOneTimeEvent.RequireAuth)
                EventStatus.EXECUTED
            }

            else -> EventStatus.NOT_EXECUTED
        }
    }

    fun observeEvents(): Flow<MainOneTimeEvent> = messageBus.receiveAsFlow()

    fun onAuthSuccess() = authManager.onSuccess()
    fun onAuthFail() = authManager.onFail()
    fun onAuthError() = authManager.onError()

    private fun sendMainEvent(event: MainOneTimeEvent) {
        launchScope(event) {
            messageBus.send(
                event
            )
        }
    }
}

