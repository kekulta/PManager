package com.kekulta.pmanager.list.domain.formatters

import android.net.Uri
import androidx.core.net.toUri

fun String.toFaviconUri(): Uri {
    return "https://$this/favicon.ico".toUri()
}


fun formatName(url: String) =
    url.removePrefix("https://").split("/").first()