package com.mohanjp.mrcoopertask.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohanjp.mrcoopertask.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = uiState.typedUsername,
            onValueChange = {
                viewModel.uiAction(LoginViewModel.UiAction.TypingUsername(it))
            },
            label = {
                Text(text = stringResource(R.string.enter_username))
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = uiState.typedPassword,
            onValueChange = {
                viewModel.uiAction(LoginViewModel.UiAction.TypingPassword(it))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            label = {
                Text(text = stringResource(R.string.enter_password))
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            viewModel.uiAction(LoginViewModel.UiAction.OnLoginButtonClick)
        }) {
            Text(text = stringResource(R.string.login))
        }
    }
}