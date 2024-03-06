package com.mohanjp.mrcoopertask.data.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import java.io.File

fun ResponseBody.saveFile(destinationFile: File): Flow<NetworkResult<File>> {
    return flow {
       emit(NetworkResult.Loading())

        try {
            byteStream().use { inputStream ->

                destinationFile.outputStream().use { outputStream ->

                    val totalBytes = contentLength()

                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var progressBytes = 0L

                    var bytes = inputStream.read(buffer)

                    while(bytes > 0) {
                        outputStream.write(buffer, 0, bytes)
                        progressBytes += bytes
                        bytes = inputStream.read(buffer)

                        println("downloading bytes >> ${progressBytes * 100/totalBytes}")
                    }
                }
            }

            emit(NetworkResult.Success(destinationFile))
        } catch(e: Exception) {
            emit(NetworkResult.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}