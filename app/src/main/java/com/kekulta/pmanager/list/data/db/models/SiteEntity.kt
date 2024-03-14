package com.kekulta.pmanager.list.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = SiteEntity.TABLE_NAME
)
data class SiteEntity(
    @PrimaryKey @ColumnInfo(name = SITE_NAME) val siteName: String,
    @ColumnInfo(name = LOGIN) val login: String?,
    @ColumnInfo(name = PASSWORD) val password: String?
) {
    companion object {
        const val TABLE_NAME = "TABLE_SITES"
        const val SITE_NAME = "COLUMN_SITE_NAME"
        const val PASSWORD = "COLUMN_PASSWORD"
        const val LOGIN = "COLUMN_LOGIN"
    }
}