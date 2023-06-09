package com.example.riptidejc

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun VoiceSessionScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Text(text = "Person 1")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
            ) {
                Text(text = "Person 2")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Button 1 click action */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Button 1")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { /* Button 2 click action */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Button 2")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { /* Button 3 click action */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Button 3")
            }
        }
    }
}

@Composable
@Preview
fun VoiceSessionScreenPreview() {
    VoiceSessionScreen(navController = rememberNavController())
}
