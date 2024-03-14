package com.kekulta.pmanager.list.domain.formatters

import com.kekulta.pmanager.list.domain.models.SiteDm
import com.kekulta.pmanager.list.presentation.vo.SiteVo

class SiteVoFormatter() {
    fun format(dms: List<SiteDm>): List<SiteVo> {
        return dms.map { dm -> SiteVo(dm.siteName, dm.login) }
    }
}