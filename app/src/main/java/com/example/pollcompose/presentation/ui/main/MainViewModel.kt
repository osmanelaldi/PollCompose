package com.example.pollcompose.presentation.ui.main

import androidx.lifecycle.ViewModel
import com.example.pollcompose.interactors.GetPolls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel
@Inject
constructor(
    getPolls : GetPolls
) : ViewModel(){

}