package com.example.pollcompose.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pollcompose.R
import com.example.pollcompose.presentation.theme.Brown

@ExperimentalUnitApi
@Composable
fun AppBar(
    userImageUrl : String,
    onUserClick : (() -> Unit)? = null
){
    Surface(
        elevation = 4.dp
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(36.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ic_poll),
                    contentDescription = "Logo"
                )
                Text(
                    text = LocalContext.current.getString(R.string.app_name),
                    fontSize = TextUnit(22f, TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Brown
                )
            }
            Box(
                modifier = Modifier.size(50.dp)
                    .clickable { onUserClick?.invoke() }
            ) {
                Image(
                    modifier = Modifier.padding(5.dp).clip(CircleShape),
                    painter = if (userImageUrl.isNotEmpty()) rememberImagePainter(userImageUrl)
                    else painterResource(id = R.drawable.ic_account),
                    contentScale = ContentScale.Crop,
                    contentDescription = "userImage"
                )
            }

        }
    }
}