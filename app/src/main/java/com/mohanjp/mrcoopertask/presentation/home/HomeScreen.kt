package com.mohanjp.mrcoopertask.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohanjp.mrcoopertask.R
import com.mohanjp.mrcoopertask.data.source.remote.ServerType
import com.mohanjp.mrcoopertask.presentation.home.components.DropDownMenu
import com.mohanjp.mrcoopertask.presentation.home.components.LoadingDialog
import com.mohanjp.mrcoopertask.presentation.home.components.RatingsDialog
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToNextScreen: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { uiEvent ->
            when(uiEvent) {
                is HomeScreenViewModel.UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                HomeScreenViewModel.UiEvent.NavigateToNextScreen -> {
                    navigateToNextScreen()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(R.string.home_screen))
            })
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if(uiState.isLoading)
                LoadingDialog()

            if(uiState.needToShowDialog) {
                RatingsDialog (
                    dismissDialog = {
                        viewModel.uiAction(HomeScreenViewModel.UiAction.RatingsCancelButtonClick)
                    },
                    okButtonClick = { ratings ->
                        viewModel.uiAction(HomeScreenViewModel.UiAction.RatingsOkButtonClick(ratings))
                    },
                    maybeButtonClick = {
                        viewModel.uiAction(HomeScreenViewModel.UiAction.RatingsCancelButtonClick)
                    }
                )
            }

            Text(
                text = stringResource(R.string.select_server_type),
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(50.dp))

            DropDownMenu(
                modifier = Modifier,
                serverTypeList = ServerType.entries,
                selectedItem = uiState.serverType,
                onItemSelected = {
                    viewModel.uiAction(HomeScreenViewModel.UiAction.OnServerItemSelected(it))
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Button(onClick = {
                viewModel.uiAction(HomeScreenViewModel.UiAction.OnSubmitButtonClick)
            }) {
                Text(
                    text = stringResource(R.string.submit),
                )
            }
        }
    }
}