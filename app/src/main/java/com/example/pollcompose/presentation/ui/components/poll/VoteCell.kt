package com.example.pollcompose.presentation.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pollcompose.presentation.theme.Brown
import com.example.pollcompose.presentation.theme.ColorScheme
import com.example.pollcompose.presentation.theme.GreenLight

@ExperimentalUnitApi
@Composable
fun VoteCell(
    percentage : Float,
    scheme : ColorScheme,
    isSelected : Boolean,
    description : String,
    onSelect : () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 5.dp)
            .background(color = scheme.light, shape = RoundedCornerShape(25.dp))
            .clip(RoundedCornerShape(25.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onSelect()
            }

    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = percentage)
                .background(color = if (isSelected) scheme.dark else scheme.medium, shape = RoundedCornerShape(25.dp))
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.7f)
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    textAlign = TextAlign.Start,
                    text = description,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.W500
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(end = 20.dp),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    textAlign = TextAlign.Center,
                    color = Brown,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.W500,
                    text = "% ${String.format("%.2f",percentage * 100.toFloat())}"
                )
            }
        }

    }
}