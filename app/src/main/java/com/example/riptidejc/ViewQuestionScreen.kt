package com.example.riptidejc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError

private var firebaseManager = FirebaseManager()
private lateinit var auth: FirebaseAuth

@Composable
fun ViewQuestionScreen(
    navController: NavController,
    questionId: String?
) {
    auth = FirebaseAuth.getInstance()

    var theQuestion by remember {
        mutableStateOf<Question?>(null)
    }

    firebaseManager.getQuestionById(questionId.toString(), object: FirebaseManager.GetQuestionByIdListener {
        override fun onSuccess(question: Question) {
            theQuestion = question
        }

        override fun onError(databaseError: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

    var isMyQuestion by remember {
        mutableStateOf(false)
    }

    isMyQuestion = (theQuestion?.user.toString() == auth.currentUser?.uid.toString())


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = theQuestion?.header.toString(),
            fontSize = 22.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = theQuestion?.body.toString(),
            fontSize = 18.sp,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isMyQuestion) {
                Button(
                    enabled = false,
                    onClick = {

                    }
                ) {
                    Text(text = "Accept Tutor")
                }
                Button(
                    onClick = {

                    }
                ) {
                    Text(text = "Mark Done")
                }
            }
            else {
                Button(
                    onClick = {

                    }
                ) {
                    Text(text = "Accept Question")
                }
            }

        }
    }
}