package com.example.pollcompose.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.pollcompose.R
import com.example.pollcompose.presentation.theme.Blue
import com.example.pollcompose.presentation.theme.ErrorRed
import com.example.pollcompose.presentation.theme.GrayLight

@ExperimentalUnitApi
@Composable
fun ErrorDialog(
    message : String,
    dismissHandler : () -> Unit
){
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(5.dp))
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 25.dp),
                text = LocalContext.current.getString(R.string.error),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = TextUnit(12f, TextUnitType.Sp)
            )
            Text(
                modifier = Modifier.padding(top = 5.dp,bottom = 10.dp, start = 10.dp, end = 10.dp),
                text = message,
                color = Color.Gray,
                fontSize = TextUnit(12f, TextUnitType.Sp)
            )
            Divider(color = GrayLight, thickness = 0.5.dp)
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                onClick = { dismissHandler() }) {
                Text(
                    text = LocalContext.current.getString(R.string.dismiss),
                    fontWeight = FontWeight.Bold,
                    color = Blue,
                    fontSize = TextUnit(12f, TextUnitType.Sp)
                )
            }
        }
    }
}