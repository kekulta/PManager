package com.kekulta.pmanager.list.domain.viewmodels

import com.kekulta.pmanager.list.domain.formatters.SiteVoFormatter
import com.kekulta.pmanager.list.domain.formatters.formatName
import com.kekulta.pmanager.list.domain.models.SiteDm
import com.kekulta.pmanager.list.domain.repos.SitesRepository
import com.kekulta.pmanager.list.presentation.vo.SiteVo
import com.kekulta.pmanager.shared.AbstractCoroutineViewModel
import com.kekulta.pmanager.shared.utils.lastNum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListViewModel(
    private val sitesRepository: SitesRepository,
    private val siteVoFormatter: SiteVoFormatter,
) : AbstractCoroutineViewModel() {
    val sites = listOf(
        "https://mail.yandex.ru/?uid=596661861#message/185492009652337743",
        "https://techno-test.vk.company/test/1852/",
        "https://vk.com/favicon.ico",
        "https://stackoverflow.com/questions/27394016/how-does-one-use-glide-to-download-an-image-into-a-bitmap",
        "https://github.com/kekulta/DummyProducts/blob/main/app/src/main/java/com/kekulta/dummyproducts/features/list/presentation/customviews/ListBottomBar.kt",
        "https://developer.android.com/reference/android/graphics/drawable/BitmapDrawable",
        "https://m3.material.io/styles/typography/applying-type",
    ).map { url ->
        SiteDm(
            formatName(url),
            url.split("/").last(),
            url.split("/").lastNum(1),
        )
    }

    init {
        sitesRepository.addSites(sites)
    }

    fun observeSites(): Flow<List<SiteVo>> =
        sitesRepository.observeSites().map { dm -> siteVoFormatter.format(dm) }

}