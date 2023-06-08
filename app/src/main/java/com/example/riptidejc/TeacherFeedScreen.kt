package com.example.riptidejc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

@Composable
fun TeacherFeedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Your Feed",
            fontSize = 22.sp,
            textDecoration = TextDecoration.Underline
        )
    }
}