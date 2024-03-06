package com.mohanjp.mrcoopertask.domain.model

data class UserData(
    val userId: String,
    val username: String,
    val isRated: Boolean,
    val ratings: Int,
    val pickedImagePath: String?
)