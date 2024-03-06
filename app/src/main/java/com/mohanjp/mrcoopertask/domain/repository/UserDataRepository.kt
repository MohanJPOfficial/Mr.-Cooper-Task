package com.mohanjp.mrcoopertask.domain.repository

import com.mohanjp.mrcoopertask.data.source.remote.dto.DownloadImageRequestDto
import com.mohanjp.mrcoopertask.data.util.NetworkResult
import com.mohanjp.mrcoopertask.domain.model.StoreImagePathRequest
import com.mohanjp.mrcoopertask.domain.model.StoreRatingsRequest
import com.mohanjp.mrcoopertask.domain.model.UserData
import com.mohanjp.mrcoopertask.domain.model.UserValidateRequest
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserDataRepository {

    /**
     * local
     */
    val isUserAuthenticated: Boolean

    suspend fun validateUser(userValidateRequest: UserValidateRequest): Boolean

    suspend fun storeRatings(storeRatingsRequest: StoreRatingsRequest)

    suspend fun getUserData(): UserData?

    suspend fun insertImagePath(request: StoreImagePathRequest)

    fun logout()

    /**
     * remote
     */
    suspend fun downloadAndGetImageFile(downloadImageRequest: DownloadImageRequestDto): Flow<NetworkResult<File>>
}