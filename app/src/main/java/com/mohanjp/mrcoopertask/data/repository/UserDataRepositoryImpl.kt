package com.mohanjp.mrcoopertask.data.repository

import com.mohanjp.mrcoopertask.data.source.local.db.AppDB
import com.mohanjp.mrcoopertask.data.source.local.entitiy.UserDataEntity
import com.mohanjp.mrcoopertask.data.source.local.sample.SampleUserData
import com.mohanjp.mrcoopertask.data.source.local.sharedpreferences.SharedPreferencesHelper
import com.mohanjp.mrcoopertask.domain.model.StoreRatingsRequest
import com.mohanjp.mrcoopertask.domain.model.UserValidateRequest
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import java.util.UUID
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val appDB: AppDB
): UserDataRepository {

    override val isUserAuthenticated: Boolean
        get() = sharedPreferencesHelper.getIsAuthenticated()

    override suspend fun validateUser(userValidateRequest: UserValidateRequest): Boolean {
        val users = SampleUserData.users

        return if(
            users.containsKey(userValidateRequest.username) &&
            users[userValidateRequest.username] == userValidateRequest.password
        ) {
            storeUserData(userValidateRequest)
            true
        } else {
            false
        }
    }

    private suspend fun storeUserData(userValidateRequest: UserValidateRequest) {
        sharedPreferencesHelper.storeIsAuthenticated(true)

        val userData = appDB.userDao.getUserData()

        if(userData?.username != userValidateRequest.username) {
            appDB.userDao.clearData()

            val userDataEntity = UserDataEntity(
                id = UUID.randomUUID().toString(),
                username = userValidateRequest.username,
                isRated = false
            )

            appDB.userDao.insert(userDataEntity)
        }
    }

    override suspend fun storeRatings(storeRatingsRequest: StoreRatingsRequest) {
        appDB.userDao.storeRatings(
            ratings = storeRatingsRequest.ratings,
            userId = storeRatingsRequest.userId
        )
    }

    private fun signOut() {
        sharedPreferencesHelper.storeIsAuthenticated(false)
    }
}