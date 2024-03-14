package com.kekulta.pmanager.list.data.db.impl

import com.kekulta.pmanager.list.data.db.api.SiteDao
import com.kekulta.pmanager.list.data.db.api.SiteDataStore
import com.kekulta.pmanager.list.data.db.models.SiteEntity
import kotlinx.coroutines.flow.Flow

class SiteDataStoreImpl(private val siteDao: SiteDao) : SiteDataStore {
    override fun observeSites(): Flow<List<SiteEntity>> {
        return siteDao.observeAll()
    }

    override suspend fun addSites(siteEntities: List<SiteEntity>) {
        siteDao.insertAll(siteEntities)
    }
}