package com.kekulta.pmanager

import android.content.Context
import android.graphics.Bitmap
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkIconDataStoreImpl(private val context: Context) : NetworkIconDataStore {
    override suspend fun loadIconSync(siteName: String): Result<Bitmap> {
        val uri = "https://$siteName/favicon.ico".toUri()

        return withContext(Dispatchers.IO) {
            try {
                val res = Glide.with(context)
                    .asBitmap()
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .submit()
                    .get()
                Result.success(res)
            } catch (e: GlideException) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}