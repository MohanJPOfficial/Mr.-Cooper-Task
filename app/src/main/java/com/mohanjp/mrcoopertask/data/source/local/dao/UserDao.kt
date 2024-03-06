package com.mohanjp.mrcoopertask.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohanjp.mrcoopertask.data.source.local.entitiy.UserDataEntity
import com.mohanjp.mrcoopertask.data.source.local.entitiy.UserDataTable

@Dao
interface UserDao {

    @Upsert
    suspend fun insert(userData: UserDataEntity)

    @Query("UPDATE ${UserDataTable.TABLE_NAME} SET ${UserDataTable.Column.PICKED_IMAGE} = :imagePath where ${UserDataTable.Column.ID} = :id")
    suspend fun insertImagePath(id: String, imagePath: String)

    @Query("SELECT * FROM ${UserDataTable.TABLE_NAME}")
    suspend fun getUserData(): UserDataEntity?

    @Query("UPDATE ${UserDataTable.TABLE_NAME} SET ${UserDataTable.Column.RATINGS} = :ratings, ${UserDataTable.Column.IS_RATED} = 1 where ${UserDataTable.Column.ID} = :userId")
    suspend fun storeRatings(ratings: Int, userId: String)

    @Query("DELETE FROM ${UserDataTable.TABLE_NAME}")
    suspend fun clearData()
}