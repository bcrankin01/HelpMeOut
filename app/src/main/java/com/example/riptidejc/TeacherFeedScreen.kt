package com.example.riptidejc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DatabaseError

private var firebaseManager = FirebaseManager()

@Composable
fun TeacherFeedScreen() {

    var questions by remember {
        mutableStateOf(listOf<Question>())
    }

    firebaseManager.getAllQuestions(object: FirebaseManager.GetAllQuestionsListener {
        override fun onSuccess(questionList: List<Question>) {
            questions = questionList
        }

        override fun onError(databaseError: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

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

        LazyColumn (modifier = Modifier.weight(1f)){
            items(questions) { question ->
                CustomCard(
                    imageRes = R.drawable.ic_launcher_foreground,
                    questionHeader = question.header.toString(),
                    questionBody = question.body.toString(),
                    onClick = {
                        /*TODO*/
                    }
                )
            }
        }

    }
}