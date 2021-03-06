package com.example.pollcompose.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pollcompose.R
import com.example.pollcompose.model.CreateOption
import com.example.pollcompose.model.Poll
import com.example.pollcompose.model.Vote
import com.example.pollcompose.presentation.theme.Brown
import com.example.pollcompose.presentation.theme.ColorScheme
import com.example.pollcompose.util.DateUtils
import java.util.*

@ExperimentalUnitApi
@Composable
fun Poll(
    currentUser : String,
    poll : Poll,
    onVote : (Vote) -> Unit,
    onDelete : (Poll) -> Unit,
){
    val scheme = ColorScheme.getScheme(poll.color)
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Card(
            modifier = Modifier.padding(10.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = 2.dp
        ) {
            Column {
                if (!poll.imageUrl.isNullOrEmpty())
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        painter = rememberImagePainter(poll.imageUrl),
                        contentDescription = poll.description,
                        contentScale = ContentScale.Crop
                    )
                Text(
                    modifier = Modifier.padding(start = 20.dp,top = 10.dp),
                    text = poll.description,
                    style = MaterialTheme.typography.h3,
                    color = Brown
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    val userVote = poll.getUserVote(currentUser)
                    poll.options.forEach { option->
                        val selectedVote = option.votes?.find { it.userId == userVote?.userId }
                        val isSelected = selectedVote != null
                        option.votecount?.let {
                            VoteCell(
                                percentage = if (option.votecount == 0) option.votecount.toFloat()
                                else (option.votecount).toFloat() / (poll.votecount),
                                description = option.description,
                                scheme = scheme,
                                isSelected = isSelected,
                                onSelect = {
                                    val newVote = Vote(
                                        optionId = option.optionId,
                                        pollId = poll.pollId,
                                        userId = currentUser,
                                        voteId = if (isSelected) selectedVote!!.voteId else
                                            userVote?.voteId ?: UUID.randomUUID().toString()

                                    )
                                    onVote(newVote)
                                }
                            )
                        }
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(5.dp)
                                .clip(CircleShape),
                            painter = if (!poll.user.imageUrl.isNullOrEmpty()) rememberImagePainter(poll.user.imageUrl)
                            else painterResource(id = R.drawable.ic_account),
                            contentScale = ContentScale.Crop,
                            contentDescription = "userImage"
                        )
                        Text(
                            text = poll.user.name,
                            style = MaterialTheme.typography.body2,
                            color = Brown
                        )
                    }
                    Button(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(0.dp),
                        elevation = null,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        onClick = {
                            onDelete(poll)
                        }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Localized description",
                            tint = scheme.dark
                        )
                    }
                }
            }
        }
    }
}