package com.byteapps.serrvicewala.UIComponents

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable()
fun CommonInputField(
    modifier: Modifier = Modifier,
    placeholder: String,
    lable: String,
    textValue: (String) -> Unit,
    keyBoardOption:KeyboardOptions

    ) {

    var fieldValue by remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        value = fieldValue,
        modifier = modifier,
        onValueChange = {
            fieldValue = it
            textValue(fieldValue.toString())
        },
        label = {
            Text(text = lable)
        },
        placeholder = {
            Text(text = placeholder, color = Color.LightGray)
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onTertiary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledLabelColor = MaterialTheme.colorScheme.onTertiary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        textStyle = TextStyle(
            fontWeight = FontWeight.SemiBold
        ),
        keyboardOptions = keyBoardOption

    )
}