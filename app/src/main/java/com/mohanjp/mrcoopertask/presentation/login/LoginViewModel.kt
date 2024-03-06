package com.mohanjp.mrcoopertask.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohanjp.mrcoopertask.domain.model.UserValidateRequest
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
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

        checkUserIsAuthenticated()
    }

    private fun checkUserIsAuthenticated() = viewModelScope.launch {

        if(repository.isUserAuthenticated)
            sendEvent(UiEvent.NavigateToNextScreen)

    }

    private fun onUiAction(uiAction: UiAction) {
        when(uiAction) {
            is UiAction.TypingUsername -> {
                _uiState.update {
                    it.copy(
                        typedUsername = uiAction.username
                    )
                }
            }
            is UiAction.TypingPassword -> {
                _uiState.update {
                    it.copy(
                        typedPassword = uiAction.password
                    )
                }
            }
            UiAction.OnLoginButtonClick -> {
                validateInternal()
            }
        }
    }

    private fun validateInternal() = viewModelScope.launch {
        val username = uiState.value.typedUsername
        val password = uiState.value.typedPassword

        if(username.isEmpty() || password.isEmpty()) {

            sendEvent(
                UiEvent.ShowSnackBar(
                    message = "The fields cannot be empty"
                )
            )

            return@launch
        }

        val validateUserRequest = UserValidateRequest(
            username = username,
            password = password
        )

        val isSuccess = repository.validateUser(validateUserRequest)

        if(isSuccess) {
            sendEvent(UiEvent.ShowSnackBar("Login Success"))
            sendEvent(UiEvent.NavigateToNextScreen)
        } else
            sendEvent(UiEvent.ShowSnackBar("Incorrect username or password"))
    }

    private fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    sealed interface UiAction {
        data class TypingUsername(val username: String): UiAction
        data class TypingPassword(val password: String): UiAction
        data object OnLoginButtonClick: UiAction
    }

    sealed interface UiEvent {
        data object NavigateToNextScreen: UiEvent
        data class ShowSnackBar(val message: String): UiEvent
    }

    data class UiState(
        val typedUsername: String = "",
        val typedPassword: String = ""
    )
}