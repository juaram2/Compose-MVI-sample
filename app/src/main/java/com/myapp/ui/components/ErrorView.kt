package com.myapp.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorView(error: String) {
    CenterAlignedBox {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            Text(text = error, color = Color.Red)
        }
    }
}

@Preview
@Composable
fun EmptyErrorView() {
    ErrorView("Error!!!!")
}