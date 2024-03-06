package com.mohanjp.mrcoopertask.di

import android.content.Context
import androidx.room.Room
import com.mohanjp.mrcoopertask.data.source.local.db.AppDB
import com.mohanjp.mrcoopertask.data.source.local.sharedpreferences.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDB(
        @ApplicationContext context: Context
    ): AppDB {
        return Room.databaseBuilder(
            context = context,
            klass = AppDB::class.java,
            name = AppDB.DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(
        @ApplicationContext
        context: Context
    ) = SharedPreferencesHelper(
        context
    )
}