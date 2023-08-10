package com.dicane.csvreader.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultTextTitle(text: String) {
    Text(
        modifier = Modifier.defaultPadding(),
        text = text,
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun ListItemTextTitle(text: String) {
    Text(
        modifier = Modifier.defaultPadding(),
        text = text,
        style = TextStyle(
            color = Color.Black,
            fontSize = 16.sp
        )
    )
}

@Composable
fun DefaultHeadLine(text: String) {
    Text(
        modifier = Modifier.defaultPadding(),
        text = text,
        style = TextStyle(
            color = Color.Gray,
            fontSize = 14.sp
        )
    )
}

fun Modifier.defaultPadding() =
    this.padding(top = 8.dp, start = 16.dp, end = 20.dp)