package com.kekulta.pmanager

import android.net.Uri
import com.kekulta.pmanager.shared.AbstractCoroutineRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IconRepository(
    private val cacheDataStore: CacheDataStore,
    private val networkIconDataStore: NetworkIconDataStore,
) : AbstractCoroutineRepository() {
    suspend fun loadFavicon(siteName: String): Uri {

        val cachedIco = cacheDataStore.loadImageSync(siteName).getOrNull()
        if (cachedIco != null) {
            return cachedIco
        } else {
            val networkIco = networkIconDataStore.loadIconSync(siteName).getOrNull()
            if (networkIco != null) {
                val cacheUri = cacheDataStore.cacheImageSync(
                    siteName, networkIco
                ).getOrNull()

                if (cacheUri != null) {
                    return cacheUri
                }
            }
        }

        return Uri.parse("android.resource://com.kekulta.pmanager/" + R.drawable.baseline_network_wifi_24);
    }

    companion object : KoinComponent {
        val instance by inject<IconRepository>()
    }
}