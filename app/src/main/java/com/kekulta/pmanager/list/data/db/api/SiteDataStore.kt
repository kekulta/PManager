package com.kekulta.pmanager.list.data.db.api

import com.kekulta.pmanager.list.data.db.models.SiteEntity
import kotlinx.coroutines.flow.Flow

interface SiteDataStore {
    fun observeSites(): Flow<List<SiteEntity>>
    suspend fun addSites(siteEntities: List<SiteEntity>)
}