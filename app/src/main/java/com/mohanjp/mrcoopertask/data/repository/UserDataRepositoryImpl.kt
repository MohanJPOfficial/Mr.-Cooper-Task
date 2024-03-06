package com.mohanjp.mrcoopertask.data.repository

import com.mohanjp.mrcoopertask.data.mappers.toUserData
import com.mohanjp.mrcoopertask.data.source.local.db.AppDB
import com.mohanjp.mrcoopertask.data.source.local.entitiy.UserDataEntity
import com.mohanjp.mrcoopertask.data.source.local.sample.SampleUserData
import com.mohanjp.mrcoopertask.data.source.local.sharedpreferences.SharedPreferencesHelper
import com.mohanjp.mrcoopertask.data.source.remote.api.ImageAPI
import com.mohanjp.mrcoopertask.data.source.remote.dto.DownloadImageRequestDto
import com.mohanjp.mrcoopertask.data.util.NetworkHelper
import com.mohanjp.mrcoopertask.data.util.NetworkResult
import com.mohanjp.mrcoopertask.data.util.saveFile
import com.mohanjp.mrcoopertask.domain.model.StoreImagePathRequest
import com.mohanjp.mrcoopertask.domain.model.StoreRatingsRequest
import com.mohanjp.mrcoopertask.domain.model.UserData
import com.mohanjp.mrcoopertask.domain.model.UserValidateRequest
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import com.mohanjp.mrcoopertask.presentation.util.StorageHelper
import com.mohanjp.mrcoopertask.presentation.util.nullAsEmpty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.net.SocketTimeoutException
import java.util.UUID
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val sharedPreferencesHelper: SharedPreferencesHelper,
    private val appDB: AppDB,
    private val api: ImageAPI,
    private val storageHelper: StorageHelper,
    private val networkHelper: NetworkHelper
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

    override suspend fun getUserData(): UserData? {
        return appDB.userDao.getUserData()?.toUserData()
    }

    override suspend fun storeRatings(storeRatingsRequest: StoreRatingsRequest) {

        appDB.userDao.storeRatings(
            ratings = storeRatingsRequest.ratings,
            userId = storeRatingsRequest.userId
        )
    }

    override suspend fun insertImagePath(request: StoreImagePathRequest) {
        appDB.userDao.insertImagePath(
            id = request.userId,
            imagePath = request.imagePath
        )
    }

    override suspend fun downloadAndGetImageFile(downloadImageRequest: DownloadImageRequestDto): Flow<NetworkResult<File>> {

        try {
            if(!networkHelper.isConnectedToInternet()) {
                return flow {
                    emit(NetworkResult.Error("Please connect to the internet"))
                }
            }

            val responseBody = api.getRandomImage(downloadImageRequest.imageUrl)
            val imageFile = storageHelper.createImageFile()

            return responseBody.saveFile(imageFile)

        } catch(e: SocketTimeoutException) {

            return flow {
                emit(NetworkResult.Error(e.message.nullAsEmpty()))
            }
        }
    }

    override fun logout() {
        sharedPreferencesHelper.storeIsAuthenticated(false)
    }
}