package com.mohanjp.mrcoopertask.data.util

import android.content.Context
import com.mohanjp.mrcoopertask.presentation.util.isConnectedToNetwork
import javax.inject.Inject

class NetworkHelper @Inject constructor(
    private val context: Context
) {
    fun isConnectedToInternet(): Boolean {
        return context.isConnectedToNetwork()
    }
}