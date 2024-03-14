package com.kekulta.pmanager

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kekulta.pmanager.databinding.ActivityMainBinding
import com.kekulta.pmanager.shared.AbstractCoroutineRepository
import com.kekulta.pmanager.shared.utils.dip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val repo: SitesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sites = listOf(
            "https://mail.yandex.ru/?uid=596661861#message/185492009652337743",
            "https://techno-test.vk.company/test/1852/",
            "https://vk.com/favicon.ico",
            "https://stackoverflow.com/questions/27394016/how-does-one-use-glide-to-download-an-image-into-a-bitmap",
            "https://github.com/kekulta/DummyProducts/blob/main/app/src/main/java/com/kekulta/dummyproducts/features/list/presentation/customviews/ListBottomBar.kt",
            "https://developer.android.com/reference/android/graphics/drawable/BitmapDrawable",
            "https://m3.material.io/styles/typography/applying-type",
        )

        val rvAdapter = SitesAdapter()

        binding.sitesRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = rvAdapter
            addItemDecoration(RecyclerViewMarginDecorator(dip(8), dip(8)))
        }

        lifecycleScope.launch {
            repo.observeSites().collect { sites ->
                rvAdapter.submitList(sites)
            }
        }

        repo.addSites(sites)
    }
}

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

fun ImageView.loadFavicon(siteName: String) {
    Glide.with(this)
        .load(siteName.toFaviconUri())
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}

fun String.toFaviconUri(): Uri {
    return "https://$this/favicon.ico".toUri()
}


class SitesAdapter : ListAdapter<SiteVo, SitesAdapter.SiteViewHolder>(DIFF_CALLBACK) {
    class SiteViewHolder(private val view: SiteView) : RecyclerView.ViewHolder(view) {
        fun bind(vo: SiteVo) {
            view.bind(vo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteViewHolder {
        return SiteViewHolder(SiteView(parent.context))
    }

    override fun onBindViewHolder(holder: SiteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SiteVo>() {
            override fun areItemsTheSame(oldItem: SiteVo, newItem: SiteVo): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: SiteVo, newItem: SiteVo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
