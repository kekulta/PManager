package com.kekulta.pmanager.add.domain.viewmodels

import com.kekulta.pmanager.list.domain.repos.SitesRepository
import com.kekulta.pmanager.shared.AbstractCoroutineViewModel
import com.kekulta.pmanager.shared.events.EventDispatcher
import com.kekulta.pmanager.shared.events.EventExecutor

class AddViewModel(
    private val sitesRepository: SitesRepository,
    dispatcher: EventDispatcher,
) : AbstractCoroutineViewModel(), EventExecutor, EventDispatcher by dispatcher {
    init {
        attach(this)
    }

    fun saveSite(address: String, login: String, password: String) {
        sitesRepository.addSite(address, login, password)
    }
}