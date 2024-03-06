package com.mohanjp.mrcoopertask.di

import android.content.Context
import androidx.room.Room
import com.mohanjp.mrcoopertask.data.source.local.db.AppDB
import com.mohanjp.mrcoopertask.data.source.local.sharedpreferences.SharedPreferencesHelper
import com.mohanjp.mrcoopertask.data.source.remote.api.ImageAPI
import com.mohanjp.mrcoopertask.data.util.NetworkHelper
import com.mohanjp.mrcoopertask.presentation.util.StorageHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient
    ): ImageAPI {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://localhost/") /* dummy url to avoid crash */
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideStorageHelper(
        @ApplicationContext context: Context
    ) = StorageHelper(context)

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ) = NetworkHelper(context)
}