package com.kekulta.pmanager.list.data.db.mappers

import com.kekulta.pmanager.list.domain.models.SiteDm
import com.kekulta.pmanager.list.data.db.models.SiteEntity

class SiteEntityMapper {
    fun map(dms: List<SiteDm>): List<SiteEntity> {
        return dms.map { dm -> SiteEntity(dm.siteName, dm.login, dm.password) }
    }
}