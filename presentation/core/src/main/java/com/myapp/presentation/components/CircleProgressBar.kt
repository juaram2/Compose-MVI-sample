package com.myapp.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleProgressBar() {
    CenterAlignedBox {
        CircularProgressIndicator(modifier = Modifier.size(100.dp), strokeWidth = 5.dp)
    }
}

@Preview
@Composable
fun CircleProgressBarPreview() {
    CircleProgressBar()
}