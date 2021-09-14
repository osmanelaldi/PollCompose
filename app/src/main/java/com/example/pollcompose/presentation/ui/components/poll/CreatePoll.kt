package com.example.pollcompose.presentation.ui.components.poll

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.pollcompose.R
import com.example.pollcompose.model.CreateOption
import com.example.pollcompose.model.CreatePollItem
import com.example.pollcompose.model.Vote
import com.example.pollcompose.presentation.theme.Brown
import com.example.pollcompose.presentation.theme.ColorScheme
import com.example.pollcompose.presentation.theme.GreenLight
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalUnitApi
@Composable
fun CreatePoll(
    onPollCreate: (CreatePollItem) -> Unit,
    onDismiss : () -> Unit,
){
    val title = remember{mutableStateOf("")}
    val options = remember{ mutableStateOf(listOf<CreateOption>())}
    val colorScheme = remember{ mutableStateOf<ColorScheme>(ColorScheme.BlueScheme)}
    val colorSchemes = ColorScheme.getSchemes()

    fun reloadOptions(updatedOptions : List<CreateOption>){
        options.value = listOf()
        options.value = updatedOptions
    }

    Card(
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = LocalContext.current.getString(R.string.new_poll),
                    style = MaterialTheme.typography.h3
                )
                Button(
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = { onDismiss() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        tint = Brown,
                        contentDescription = "DismissPoll"
                    )
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                shape = RoundedCornerShape(10.dp),
                value = title.value,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = colorScheme.value.light,
                    cursorColor = Brown,
                    disabledLabelColor = Brown,
                    unfocusedLabelColor = Brown,
                    focusedLabelColor = Brown,
                    textColor = Brown,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                label = { Text(text = LocalContext.current.getString(R.string.title))},
                onValueChange = { value->
                    title.value = value
                },
                trailingIcon = {
                    if (title.value.isNotEmpty()) {
                        IconButton(onClick = {
                            title.value = ""
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h4,
                text = LocalContext.current.getString(R.string.options)
            )
                options.value.forEach { createOption ->
                    CreateOption(
                        createOption = createOption,
                        colorScheme = colorScheme.value,
                        onOptionUpdate = { option ->
                            reloadOptions(options.value.updateOptions(option))
                        },
                        onDismiss = { option ->
                            reloadOptions(options.value.removeOption(option))
                        }
                    )
                }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    shape = RoundedCornerShape(10.dp),
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    onClick = {
                        reloadOptions(options.value.addOption(CreateOption()))
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "AddOption",
                        tint = Brown
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp,horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                colorSchemes.forEach { scheme->
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                            .background(color = scheme.medium, shape = RoundedCornerShape(10.dp))
                            .clip(shape = RoundedCornerShape(10.dp))
                            .clickable {
                                colorScheme.value = scheme
                            }
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    shape = RoundedCornerShape(10.dp),
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorScheme.value.dark,
                        disabledBackgroundColor = Color.Gray
                    ),
                    onClick = {
                        val createPollItem = CreatePollItem(
                            description = title.value,
                            createOptions = options.value,
                            color = colorScheme.value.key
                        )
                        onPollCreate(createPollItem)
                        },
                    enabled = options.value.joinToString { it.description }.isNotEmpty()
                            && title.value.isNotEmpty()
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.create)
                    )
                }
            }
        }
    }
}

@Composable
fun CreateOption(
    createOption: CreateOption,
    colorScheme : ColorScheme,
    onOptionUpdate : (CreateOption) -> Unit,
    onDismiss: (CreateOption) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(vertical = 5.dp),
            value = createOption.description,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorScheme.light,
                cursorColor = Color.Black,
                disabledLabelColor = Brown,
                unfocusedLabelColor = Brown,
                focusedLabelColor = Brown,
                textColor = Brown,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            shape = RoundedCornerShape(10.dp),
            label = { Text(text = LocalContext.current.getString(R.string.option))},
            onValueChange = { inputValue->
                createOption.description = inputValue
                onOptionUpdate(createOption)
            },
            trailingIcon = {
                if (createOption.description.isNotEmpty()) {
                    IconButton(onClick = {
                        createOption.description = ""
                        onOptionUpdate(createOption)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        Button(
            modifier = Modifier.fillMaxWidth(1f),
            shape = RoundedCornerShape(10.dp),
            elevation = null,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            onClick = {
                onDismiss(createOption)
            }){
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                tint = Brown,
                contentDescription = "DismissPoll"
            )
        }
    }
}

private fun List<CreateOption>.updateOptions(createOption: CreateOption) : List<CreateOption>{
    this.find { it.optionId == createOption.optionId}?.description = createOption.description
    return this
}

private fun List<CreateOption>.addOption(createOption: CreateOption) : List<CreateOption>{
    val temp = ArrayList(this)
    temp.add(createOption)
    return temp
}

private fun List<CreateOption>.removeOption(createOption: CreateOption) : List<CreateOption>{
    val temp = ArrayList(this)
    temp.remove(createOption)
    return temp
}