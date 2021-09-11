package com.example.pollcompose.presentation.ui.components

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.*

class DialogQueue {

    val queue : MutableState<Queue<MessageDialog>> = mutableStateOf(LinkedList())


    fun appendDialog(messageDialog: MessageDialog){
        queue.value.offer(messageDialog)
        val update = queue.value
        queue.value = ArrayDeque()
        queue.value = update
    }

    fun removeDialog(){
        if (queue.value.isNotEmpty()){
            queue.value.remove()
            val update = queue.value
            queue.value = ArrayDeque()
            queue.value = update
        }
    }

}

sealed class MessageDialog{
    data class Error(val message : String) : MessageDialog()
}