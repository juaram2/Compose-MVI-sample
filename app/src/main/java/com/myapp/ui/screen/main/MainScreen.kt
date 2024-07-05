package com.myapp.ui.screen.main

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.myapp.ui.components.CircleProgressBar
import com.myapp.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.annotation.OrbitInternal

@OptIn(OrbitInternal::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainScreen(viewModel: MainViewModel = MainViewModel()) {
    val context = LocalContext.current
    val number = remember { mutableStateOf("0") }
    val isLoading = remember { mutableStateOf(false) }

    rememberCoroutineScope().launch {
        viewModel.container.stateFlow.collect {
            when (it.randomNumberState) {
                is MainContract.RandomNumberState.Loading -> {

                }
                is MainContract.RandomNumberState.Success -> {
                    number.value = it.randomNumberState.number.toString()
                }
                is MainContract.RandomNumberState.Fail -> {}
                is MainContract.RandomNumberState.Idle -> {}
            }
        }
//        viewModel.uiState.collect {
//            when (it.randomNumberState) {
//                is MainContract.RandomNumberState.Fail -> { isLoading.value = false }
//                is MainContract.RandomNumberState.Loading -> { isLoading.value = true }
//                is MainContract.RandomNumberState.Success -> {
//                    isLoading.value = false
//                    number.value = it.randomNumberState.number.toString()
//                }
//            }
//        }
    }

    rememberCoroutineScope().launch {
        viewModel.container.sideEffectFlow.collect {
            when (it) {
                is MainContract.Effect.ShowToast -> {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
//        viewModel.effect.collect {
//            when (it) {
//                is MainContract.Effect.ShowToast -> {
//                    isLoading.value = false
//
//                    val message = "Error, number is even"
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    if (isLoading.value) {
        CircleProgressBar()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            onClick = { viewModel.generateRandomNumber() },
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "Random : ${number.value}")
        }

        TextButton(
            onClick = { /*viewModel.setIntent(MainContract.Intent.OnShowToastClicked)*/ },
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "Show Toast")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        MainScreen()
    }
}