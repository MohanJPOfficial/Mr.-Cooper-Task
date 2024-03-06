package com.mohanjp.mrcoopertask.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mohanjp.mrcoopertask.R

@Composable
fun RatingsDialog(
    dismissDialog: (Boolean) -> Unit,
    okButtonClick: (Int) -> Unit,
    maybeButtonClick: () -> Unit
) {

    val ratingsState = remember {
        mutableIntStateOf(0)
    }

    Dialog(onDismissRequest = { dismissDialog(false) }) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.kindly_rate_the_app),
                style = MaterialTheme.typography.titleMedium
            )

            UserRatingsBar(
                size = 40.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp),
                ratingState = ratingsState
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if(ratingsState.intValue > 0) {
                        okButtonClick(ratingsState.intValue)
                    }
                }) {
                    Text(text = stringResource(R.string.ok))
                }

                Button(onClick = {
                    maybeButtonClick()
                }) {
                    Text(text = stringResource(R.string.maybe_later))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}