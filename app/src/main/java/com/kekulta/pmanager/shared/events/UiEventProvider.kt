package com.kekulta.pmanager.shared.events

import com.kekulta.pmanager.shared.events.UiEvent
import com.kekulta.pmanager.shared.events.UiEventCallback
import logcat.logcat

interface UiEventProvider {
    var uiEventCallback: UiEventCallback?

    fun dispatch(event: UiEvent) {
        if (uiEventCallback == null) {
            logcat { "Lost event: $event" }
        }
        uiEventCallback?.invoke(event)
    }
}