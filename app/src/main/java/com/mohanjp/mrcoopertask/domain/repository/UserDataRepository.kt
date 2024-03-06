package com.mohanjp.mrcoopertask.domain.repository

import com.mohanjp.mrcoopertask.domain.model.StoreRatingsRequest
import com.mohanjp.mrcoopertask.domain.model.UserValidateRequest

interface UserDataRepository {

    val isUserAuthenticated: Boolean

    suspend fun validateUser(userValidateRequest: UserValidateRequest): Boolean

    suspend fun storeRatings(storeRatingsRequest: StoreRatingsRequest)
}