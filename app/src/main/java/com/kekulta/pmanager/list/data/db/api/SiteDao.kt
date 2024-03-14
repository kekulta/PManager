package com.kekulta.pmanager.list.data.db.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kekulta.pmanager.list.data.db.models.SiteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SiteDao {
    @Query(
        """
        SELECT * FROM ${SiteEntity.TABLE_NAME}
    """
    )
    fun observeAll(): Flow<List<SiteEntity>>

    @Query(
        """
        SELECT * FROM ${SiteEntity.TABLE_NAME} WHERE ${SiteEntity.SITE_NAME} = :siteName 
    """
    )
    suspend fun getBySiteName(siteName: String): SiteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SiteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<SiteEntity>)

    @Query(
        """
            DELETE FROM ${SiteEntity.TABLE_NAME} WHERE ${SiteEntity.SITE_NAME} = :siteName 
        """
    )
    suspend fun deleteBySiteName(siteName: String)
}