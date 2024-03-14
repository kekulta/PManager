package com.kekulta.pmanager.shared.events

import logcat.LogPriority
import logcat.logcat

class Dispatcher : EventDispatcher {
    private val executors = mutableSetOf<EventExecutor>()

    override fun dispatch(event: Event): EventStatus {
        var executed = false
        for (e in executors) {
            when (e.execute(event)) {
                EventStatus.EXECUTED -> {
                    executed = true
                    break
                }

                EventStatus.NOT_EXECUTED -> logcat { "Executor $e couldn't execute $event" }
            }
        }

        return if (!executed) {
            logcat(LogPriority.ERROR) { "Event $event wasn't executed!" }
            EventStatus.NOT_EXECUTED
        } else {
            EventStatus.EXECUTED
        }
    }

    override fun attach(executor: EventExecutor) {
        executors.add(executor)
    }
}

interface EventDispatcher {
    fun dispatch(event: Event): EventStatus
    fun attach(executor: EventExecutor)
}