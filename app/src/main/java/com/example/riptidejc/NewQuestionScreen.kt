package com.example.riptidejc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.google.firebase.database.DatabaseError

private var firebaseManager = FirebaseManager()

@Composable
fun NewQuestionScreen() {

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
        mutableStateOf("Select a class")
    }

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
            text = "$selectedCourse",
            modifier = Modifier.padding(10.dp),
        ) {
            for (course in courses) {
                Text(
                    text = course.second.name.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .clickable { selectedCourse = course.second.name.toString()},
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
    }
}


