package com.mohanjp.mrcoopertask.di

import com.mohanjp.mrcoopertask.data.repository.UserDataRepositoryImpl
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AbstractAppModule {

    @Binds
    @Singleton
    fun bindUserDataRepository(
        userDataRepositoryImpl: UserDataRepositoryImpl
    ): UserDataRepository
}