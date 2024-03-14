package com.kekulta.pmanager.list.data.db.mappers

import com.kekulta.pmanager.list.domain.models.SiteDm
import com.kekulta.pmanager.list.data.db.models.SiteEntity

class SiteDmMapper() {
    fun map(dtos: List<SiteEntity>): List<SiteDm> {
        return dtos.map { dto -> SiteDm(dto.siteName, dto.login, dto.password) }
    }
}