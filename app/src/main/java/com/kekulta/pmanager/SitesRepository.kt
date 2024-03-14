package com.kekulta.pmanager

import com.kekulta.pmanager.shared.AbstractCoroutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map

class SitesRepository : AbstractCoroutineRepository() {
    private val sites = MutableStateFlow<List<String>>(listOf())

    fun observeSites(): Flow<List<SiteVo>> {
        return sites.map { sitesNames ->
            sitesNames.map { siteName ->
                SiteVo(siteName)
            }
        }
    }

    fun addSites(urls: List<String>) {
        sites.getAndUpdate { old -> old + urls.map { url -> formatName(url) } }
    }

    private fun formatName(url: String) =
        url.removePrefix("https://").split("/").first()
}