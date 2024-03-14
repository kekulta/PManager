package com.kekulta.pmanager.list.domain.repos

import com.kekulta.pmanager.list.data.db.api.SiteDataStore
import com.kekulta.pmanager.list.data.db.mappers.SiteDmMapper
import com.kekulta.pmanager.list.data.db.mappers.SiteEntityMapper
import com.kekulta.pmanager.list.domain.formatters.formatName
import com.kekulta.pmanager.list.domain.models.SiteDm
import com.kekulta.pmanager.shared.AbstractCoroutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SitesRepository(
    private val siteDataStore: SiteDataStore,
    private val siteDmMapper: SiteDmMapper,
    private val siteEntityMapper: SiteEntityMapper,
) : AbstractCoroutineRepository() {

    fun observeSites(): Flow<List<SiteDm>> {
        return siteDataStore.observeSites().map { dtos ->
            siteDmMapper.map(dtos)
        }
    }

    fun addSites(dms: List<SiteDm>) {
        launchScope(dms) {
            siteDataStore.addSites(siteEntityMapper.map(dms))
        }
    }

    fun addSite(address: String, login: String, password: String) {
        val dm = SiteDm(formatName(address), login, password)

        launchScope(dm) {
            siteDataStore.addSites(siteEntityMapper.map(listOf(dm)))
        }
    }
}

