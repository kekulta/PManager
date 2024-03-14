package com.kekulta.pmanager

import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadFavicon(siteName: String) {
    Glide.with(this)
        .load(siteName.toFaviconUri())
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .into(this)
}

fun String.toFaviconUri(): Uri {
    return "https://$this/favicon.ico".toUri()
}
