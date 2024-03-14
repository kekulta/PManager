package com.kekulta.pmanager.shared.events

interface UiEventDispatcher {
    fun dispatch(event: UiEvent)
    fun attachUiEventProvider(provider: UiEventProvider) {
        provider.uiEventCallback = ::dispatch
    }
}