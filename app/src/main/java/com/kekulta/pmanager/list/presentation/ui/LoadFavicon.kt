package com.kekulta.pmanager.list.presentation.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kekulta.pmanager.R
import com.kekulta.pmanager.list.domain.formatters.toFaviconUri

fun ImageView.loadFavicon(siteName: String) {
    Glide.with(this)
        .load(siteName.toFaviconUri())
        .error(R.drawable.baseline_network_wifi_24)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}
