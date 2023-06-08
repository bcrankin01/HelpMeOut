package com.example.riptidejc

import android.content.Intent
import android.graphics.fonts.FontStyle
import android.util.Log
import android.widget.Toast
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DatabaseError

private lateinit var auth: FirebaseAuth
private val firebaseManager = FirebaseManager()

@Composable
fun SigninScreen(
    navController: NavController
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        var info = InitialView()

        Button(
            onClick = {
                Log.d("OnSignIn", "Email: ${info[0]}, Password: ${info[1]}")
                signIn(info[0], info[1], navController)
            }
        ) {
            Text(text = "Sign in")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an account?")
            TextButton(onClick = {
                navController.navigate(route = Screen.SignupScreen.route)
            }) {
                Text(
                    text = "\tRegister",
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

private fun signIn(email: String, password: String, navController: NavController) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign-in success, do something
                navController.navigate(Screen.MyQuestionsScreen.route)
            } else {
                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    // Handle invalid user error
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    // Handle invalid credentials error
                } catch (e: Exception) {
                    // Handle other exceptions
                }
            }
        }
}



@Composable
@Preview
fun SigninScreenPreview() {
    SigninScreen(
        navController = rememberNavController()
    )
}