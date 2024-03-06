package com.mohanjp.mrcoopertask.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: UserDataRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UiState()
    )

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiAction: (UiAction) -> Unit

    init {
        uiAction = ::onUiAction
    }

    private fun onUiAction(uiAction: UiAction) {
        when(uiAction) {
            UiAction.DisplayButtonClick -> {
                getImageFromLocal()
            }
            UiAction.LogoutButtonClick -> {
                repository.logout()

                sendEvent(UiEvent.NavigateToLoginScreen)
            }
        }
    }

    private fun getImageFromLocal() = viewModelScope.launch {

        if(uiState.value.imageFile != null) {
            return@launch
        }

        val imagePath = repository.getUserData()?.pickedImagePath


        if(imagePath == null) {
            sendEvent(UiEvent.ShowSnackBar("Something went wrong"))
        } else {
            _uiState.update {
                it.copy(
                    imageFile = File(imagePath)
                )
            }
        }
    }

    private fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    sealed interface UiAction {
        data object LogoutButtonClick: UiAction
        data object DisplayButtonClick: UiAction
    }

    sealed interface UiEvent {
        data object NavigateToLoginScreen: UiEvent
        data class ShowSnackBar(val message: String): UiEvent
    }

    data class UiState(
        val imageFile: File? = null
    )
}