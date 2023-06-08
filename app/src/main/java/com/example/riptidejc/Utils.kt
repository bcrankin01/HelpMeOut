package com.example.riptidejc

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController

@Composable
fun InitialView(): List<String> {
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }




    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Image(
            painter = painterResource(id = R.drawable.riptidelogo),
            contentDescription = null,
            modifier = Modifier
                .size(230.dp)
        )
        Text(
            text = "HelpMeOut",
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )

        Spacer(
            modifier = Modifier.size(32.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { text ->
                username = text
            },
            label = { Text("Username") },
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        OutlinedTextField(
            modifier = Modifier
                .background(Color.White),
            value = password,
            onValueChange = { text ->
                password = text
            },
            label = { Text("Password") }, // Hint for the password field
        )
    }
    return listOf(username, password)
}

@Composable
fun DropDown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean = false,
    content: @Composable () -> Unit,

) {
    var isOpen by remember { mutableStateOf(initiallyOpened) }

    val alpha = animateFloatAsState(
        targetValue = if (isOpen) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )

    val rotateX = animateFloatAsState(
        targetValue = if (isOpen) 0f else -90f,
        animationSpec = tween(durationMillis = 300)
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .clickable {
                    isOpen = !isOpen
                }

        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open or close the drop down",
                tint = Color.White,
                modifier = Modifier
                    .scale(1f, if (isOpen) -1f else 1f)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 0f)
                    rotationX = rotateX.value
                }
                .alpha(alpha.value)
        ) {
            content()
        }
    }
}



@Composable
@Preview
fun UtilsPreview() {
    InitialView()
}