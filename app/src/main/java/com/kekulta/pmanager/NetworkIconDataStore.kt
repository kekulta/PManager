package com.kekulta.pmanager

import android.graphics.Bitmap

interface NetworkIconDataStore {
    suspend fun loadIconSync(siteName: String): Result<Bitmap>
}