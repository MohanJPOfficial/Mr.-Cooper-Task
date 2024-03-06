package com.mohanjp.mrcoopertask.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohanjp.mrcoopertask.data.source.local.dao.UserDao
import com.mohanjp.mrcoopertask.data.source.local.entitiy.UserDataEntity

@Database(
    entities = [UserDataEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDB : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DB_NAME = "user_data.db"
    }
}