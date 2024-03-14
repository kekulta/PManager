package com.kekulta.pmanager

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.net.toFile
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.io.IOException

class CacheDataStoreImpl(private val context: Context) : CacheDataStore {
    init {
        //clearCache()
    }

    override suspend fun loadImageSync(name: String): Result<Uri> {
        val uri = getUri(name)
        return withContext(Dispatchers.IO) {
            try {
                val file = uri.toFile()
                if (file.exists()) {
                    Result.success(uri)
                } else {
                    Result.failure(
                        NoSuchFileException(
                            file, reason = "Cache does not exist!"
                        )
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }

    override suspend fun cacheImageSync(name: String, image: Bitmap): Result<Uri> {
        return try {
            val uri = getUri(name)
            withContext(Dispatchers.IO) {
                FileOutputStream(uri.toFile()).use { out ->
                    image.compress(
                        Bitmap.CompressFormat.PNG, 100, out
                    )
                }
            }
            Result.success(uri)
        } catch (e: IOException) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private fun getUri(name: String): Uri {
        return "file://${context.cacheDir}/cached_$name.ico".toUri()
    }

    private fun clearCache() {
        context.cacheDir.listFiles()?.forEach { file -> file.deleteRecursively() }
    }
}