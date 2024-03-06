package com.mohanjp.mrcoopertask.data.source.local.sharedpreferences

import android.content.Context
import javax.inject.Inject

class SharedPreferencesHelper @Inject constructor(
    private val context: Context
) {
    private val sharedPreferences by lazy {
        context.getSharedPreferences(
            /* name = */ SHARED_PREFERENCES_NAME,
            /* mode = */ Context.MODE_PRIVATE
        )
    }

    fun storeIsAuthenticated(isAuthenticated: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(IS_AUTHENTICATED_KEY, isAuthenticated)
            apply()
        }
    }

    fun getIsAuthenticated(): Boolean {
        return sharedPreferences.getBoolean(IS_AUTHENTICATED_KEY, false)
    }

    private companion object {
        const val SHARED_PREFERENCES_NAME = "user_preferences"
        const val IS_AUTHENTICATED_KEY = "IS_AUTHENTICATED"
    }
}