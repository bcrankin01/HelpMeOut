package com.example.riptidejc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError

private var firebaseManager = FirebaseManager()
private lateinit var auth: FirebaseAuth

@Composable
fun NewQuestionScreen(
    navController: NavController
) {

    var courses by remember {
        mutableStateOf(listOf<Pair<String, Course>>())
    }

    var header by remember {
        mutableStateOf("")
    }

    var body by remember {
        mutableStateOf("")
    }

    var selectedCourse by remember {
        mutableStateOf<Pair<String, Course>?>(null)
    }

    auth = FirebaseAuth.getInstance()

    firebaseManager.getStudentEnrollments(object: FirebaseManager.GetStudentEnrollmentsListener {
        override fun onSuccess(courseList: List<Pair<String, Course>>) {
            courses = courseList
        }

        override fun onError(databaseError: DatabaseError) {
            TODO("Not yet implemented")
        }

    })



    Column (
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        Text(
            text = "New Question",
            style = MaterialTheme.typography.headlineSmall,
            textDecoration = TextDecoration.Underline,
        )
        Spacer(modifier = Modifier.height(10.dp))
        DropDown(
            text = "${selectedCourse?.second?.name ?: "Select a course"}",
            modifier = Modifier.padding(10.dp),
        )
        {
            for (course in courses) {
                Text(
                    text = course.second.name.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clickable { selectedCourse = course },
                )
            }
        }


        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .background(Color.White),
            value = header,
            onValueChange = { text ->
                header = text
            },
            label = { Text("Header") }, // Hint for the password field
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .background(Color.White),
            value = body,
            onValueChange = { text ->
                body = text
            },
            label = { Text("Body") }, // Hint for the password field
        )
        Button(
            onClick = {
                firebaseManager.postQuestion(selectedCourse?.first.toString(), header, body)
                navController.navigate("myquestions_screen")
            }
        ){
            Text(text = "Post")
        }
    }
}


