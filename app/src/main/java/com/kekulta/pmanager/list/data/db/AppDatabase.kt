package com.kekulta.pmanager.list.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kekulta.pmanager.list.data.db.api.SiteDao
import com.kekulta.pmanager.list.data.db.models.SiteEntity

@Database(
    entities = [SiteEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSiteDao(): SiteDao

    companion object {
        fun createDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext, AppDatabase::class.java, "pmmanager-db"
            ).build()
        }
    }
}