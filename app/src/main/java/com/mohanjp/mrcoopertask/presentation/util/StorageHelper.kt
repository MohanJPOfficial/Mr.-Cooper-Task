package com.mohanjp.mrcoopertask.presentation.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.time.LocalDateTime
import javax.inject.Inject

class StorageHelper @Inject constructor(
    private val context: Context
) {
    fun createImageFile(): File {
        val directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            getRandomName(),
            FileExtension.JPG.extension,
            directory
        )
    }

    private fun getRandomName(): String {
        return "Image_${LocalDateTime.now()}"
    }
}

enum class FileExtension(val extension: String) {
    JPG(".jpg"),
    PNG(".png")
}