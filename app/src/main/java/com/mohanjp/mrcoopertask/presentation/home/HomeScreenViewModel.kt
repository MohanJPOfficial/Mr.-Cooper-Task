package com.mohanjp.mrcoopertask.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohanjp.mrcoopertask.domain.model.StoreRatingsRequest
import com.mohanjp.mrcoopertask.domain.repository.UserDataRepository
import com.mohanjp.mrcoopertask.presentation.util.nullAsEmpty
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
class HomeScreenViewModel @Inject constructor(
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
        storeUserIdAndIsRated()

        uiAction = ::onUiAction
    }

    private fun onUiAction(uiAction: UiAction) {
        when(uiAction) {
            is UiAction.RatingsOkButtonClick -> {

                storeRatings(uiAction.ratings)

                dismissRatingsDialog()
            }
            UiAction.RatingsCancelButtonClick -> {
                dismissRatingsDialog()
            }
        }
    }

    private fun storeRatings(ratings: Int) = viewModelScope.launch {

        val storeRatingsRequest = StoreRatingsRequest(
            ratings = ratings,
            userId = uiState.value.userId
        )

        repository.storeRatings(
            storeRatingsRequest
        )

        sendEvent(UiEvent.ShowSnackBar("Thanks for the ratings"))
    }

    private fun storeUserIdAndIsRated() = viewModelScope.launch {

        val userdata = repository.getUserData()

        _uiState.update {
            it.copy(
                userId = userdata?.userId.nullAsEmpty(),
                needToShowDialog = userdata?.isRated != true
            )
        }
    }

    private fun dismissRatingsDialog() {
        _uiState.update {
            it.copy(
                needToShowDialog = false
            )
        }
    }

    private fun sendEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    sealed interface UiEvent {
        data object NavigateToNextScreen: UiEvent
        data class ShowSnackBar(val message: String): UiEvent
    }

    sealed interface UiAction {
        data class RatingsOkButtonClick(var ratings: Int): UiAction
        data object RatingsCancelButtonClick: UiAction
    }

    data class UiState(
        val needToShowDialog: Boolean = false,
        val userId: String = ""
    )
}