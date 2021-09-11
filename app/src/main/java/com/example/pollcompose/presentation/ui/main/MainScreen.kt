package com.example.pollcompose.presentation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pollcompose.presentation.ui.components.AppBar
import com.example.pollcompose.presentation.ui.components.common.AppTheme
import com.example.pollcompose.presentation.ui.components.Poll
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

                LazyColumn(modifier = Modifier.fillMaxWidth()){
                    itemsIndexed(items = viewModel.polls.value){ index, poll->
                        Poll(
                            poll = poll,
                            currentUser = userId,
                            onVote = {vote -> viewModel.vote(vote)}
                        )
                    }
                }
            }
        }
    }
    viewModel.getUser(userId)
}