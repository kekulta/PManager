package com.kekulta.pmanager

import android.graphics.Bitmap
import android.net.Uri

interface CacheDataStore {
    suspend fun loadImageSync(name: String): Result<Uri>
    suspend fun cacheImageSync(name: String, image: Bitmap): Result<Uri>
}