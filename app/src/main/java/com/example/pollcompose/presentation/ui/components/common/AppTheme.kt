package com.example.pollcompose.presentation.ui.components.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.example.pollcompose.presentation.theme.SanfTypography
import com.example.pollcompose.presentation.ui.components.ErrorDialog
import com.example.pollcompose.presentation.ui.components.MessageDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalUnitApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun AppTheme(
    dialogQueue: Queue<MessageDialog>? = null,
    dialogDismiss : () -> Unit,
    content: @Composable () -> Unit
){
    MaterialTheme(
        typography = SanfTypography
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
            ProcessDialogQueue(dialogQueue = dialogQueue, dialogDismiss = dialogDismiss)
        }
    }
}


@ExperimentalUnitApi
@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<MessageDialog>?,
    dialogDismiss: (() -> Unit)?,
) {
    dialogQueue?.peek()?.let { dialog ->
        when(dialog){
            is MessageDialog.Error ->{
                ErrorDialog(message = dialog.message) {
                    dialogDismiss?.invoke()
                }
            }
        }
    }
}