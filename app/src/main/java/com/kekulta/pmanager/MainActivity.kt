package com.kekulta.pmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.pmanager.databinding.ActivityMainBinding
import com.kekulta.pmanager.shared.utils.dip
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

