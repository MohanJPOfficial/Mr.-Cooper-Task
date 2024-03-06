package com.mohanjp.mrcoopertask.data.source.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = UserDataTable.TABLE_NAME
)
data class UserDataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(UserDataTable.Column.ID)
    val id: String,

    @ColumnInfo(UserDataTable.Column.USERNAME)
    val username: String,

    @ColumnInfo(UserDataTable.Column.IS_RATED)
    val isRated: Boolean = false,

    @ColumnInfo(UserDataTable.Column.RATINGS)
    val ratings: Int = 0,

    @ColumnInfo(UserDataTable.Column.PICKED_IMAGE)
    val pickedImage: String? = null
)

object UserDataTable {
    const val TABLE_NAME = "user_data"

    object Column {
        const val ID = "id"
        const val USERNAME = "username"
        const val RATINGS = "ratings"
        const val IS_RATED = "is_rated"
        const val PICKED_IMAGE = "picked_image"
    }
}
