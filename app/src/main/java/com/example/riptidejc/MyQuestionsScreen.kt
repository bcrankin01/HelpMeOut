package com.example.riptidejc

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.ktx.Firebase

private var firebaseManager = FirebaseManager()

@Composable
fun MyQuestionsScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Questions",
            fontSize = 22.sp,
            textDecoration = TextDecoration.Underline
        )

        var questions by remember {
            mutableStateOf(listOf<Pair<String,Question>>())
        }

        firebaseManager.getQuestionsByUser(object: FirebaseManager.GetQuestionsByUserListener {
            override fun onSuccess(questionList: List<Pair<String,Question>>) {
                questions = questionList
            }

            override fun onError(databaseError: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        LazyColumn (modifier = Modifier.weight(1f)){
            items(questions) { question ->
                CustomCard(
                    imageRes = R.drawable.ic_launcher_foreground,
                    questionHeader = question.second.header.toString(),
                    questionBody = question.second.body.toString(),
                    status = question.second.complete,
                    onClick = {
                        Log.d("QuestionOnClick", "Hit: ${question.second.header.toString()}")
                        navController.navigate(Screen.ViewQuestionScreen.route + "/${question.first}")
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate(Screen.NewQuestionScreen.route) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
            content = {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCard(imageRes: Int,
               questionHeader: String,
               questionBody: String,
               status: String,
               onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation =CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = questionHeader,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = questionBody,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Blue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = status,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .background(
                            when (status) {
                                "Pending Tutor" -> Color.Yellow
                                "Pending User Acceptance" -> Color.Red
                                "In Progress" -> Color.Green
                                else -> Color.Transparent
                            }
                        )
                )

            }
        }
    }
}

@Composable
@Preview
fun MyQuestionsScreenPreview() {
    MyQuestionsScreen(navController = rememberNavController())
}
