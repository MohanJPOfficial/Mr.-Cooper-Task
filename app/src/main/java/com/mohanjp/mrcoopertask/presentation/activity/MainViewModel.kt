package com.mohanjp.mrcoopertask.presentation.activity

import androidx.lifecycle.ViewModel
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: UserDataRepository
): ViewModel() {

    fun checkUserIsAuthenticated(): Boolean {
        return repository.isUserAuthenticated
    }
}