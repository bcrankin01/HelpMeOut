package com.example.riptidejc

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignupScreen(
    navController: NavController
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        InitialView()

        Spacer(
            modifier = Modifier.size(32.dp)
        )

        Button(
            onClick = {

            }
        ) {
            Text(text = "Register")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an account?")
            TextButton(onClick = {
                navController.navigate(route = Screen.SigninScreen.route)
            }) {
                Text(
                    text = "\tSign in",
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}

@Composable
@Preview
fun SignupScreenPreview() {
    SignupScreen(
        navController = rememberNavController()
    )
}