package com.example.pollcompose.presentation.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pollcompose.R
import com.example.pollcompose.presentation.theme.Brown
import com.example.pollcompose.presentation.theme.ColorScheme
import com.example.pollcompose.presentation.ui.components.AppBar
import com.example.pollcompose.presentation.ui.components.common.AppTheme
import com.example.pollcompose.presentation.ui.components.Poll
import com.example.pollcompose.presentation.ui.components.poll.CreatePoll
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalMaterialApi
@ExperimentalUnitApi
@ExperimentalCoroutinesApi
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    userId : String,
    navigatePage : (String) -> Unit
) {
    AppTheme(
        dialogQueue = viewModel.dialogQueue.queue.value,
        dialogDismiss = {viewModel.dialogQueue.removeDialog()}
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AppBar(userImageUrl = viewModel.user.value?.imageUrl ?: "")

                if (viewModel.createVisibility.value)
                    CreatePoll(
                        onPollCreate = { createPollItem ->
                            viewModel.createPoll(createPollItem)
                        },
                        onDismiss = { viewModel.createVisibility.value = false }
                    )
                else
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ColorScheme.BlueScheme.light,
                        ),
                        elevation = null,
                        onClick = {
                            viewModel.createVisibility.value = true
                        }
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1,
                            color = Brown,
                            text = LocalContext.current.getString(R.string.create_poll)
                        )
                    }


                LazyColumn(modifier = Modifier.fillMaxWidth()){
                    itemsIndexed(items = viewModel.polls.value){ index, poll->
                        Poll(
                            poll = poll,
                            currentUser = userId,
                            onVote = {vote -> viewModel.vote(vote)},
                            onDelete = {deletePoll -> viewModel.deletePoll(deletePoll)}
                        )
                    }
                }
            }
        }
    }
    viewModel.user.value ?: run {
        viewModel.getUser(userId)
    }
}