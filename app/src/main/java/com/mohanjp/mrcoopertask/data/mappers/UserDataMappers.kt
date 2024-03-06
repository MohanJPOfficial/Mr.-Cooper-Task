package com.mohanjp.mrcoopertask.data.mappers

import com.mohanjp.mrcoopertask.data.source.local.entitiy.UserDataEntity
import com.mohanjp.mrcoopertask.domain.model.UserData

fun UserDataEntity.toUserData() = UserData(
    userId = id,
    username = username,
    isRated = isRated,
    ratings = ratings,
    pickedImagePath = pickedImage
)