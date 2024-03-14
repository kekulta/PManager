package com.kekulta.pmanager.shared.events

import logcat.logcat

interface EventProvider {
    var eventCallback: EventCallback?

    fun dispatch(event: Event) {
        if (eventCallback == null) {
            logcat { "Lost event: $event" }
        }
        eventCallback?.invoke(event)
    }
}