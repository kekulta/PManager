package com.kekulta.pmanager.shared.events

interface EventExecutor {
    fun execute(event: Event): EventStatus = EventStatus.NOT_EXECUTED
}