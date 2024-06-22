package com.ark.truckbill.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.ark.truckbill.ui.theme.FocusColor

@Composable
fun TextInput(
    label: String,
    value: String,
    onChange: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true,
) {
    val numberVisualTransformation = VisualTransformation.None
    TextField(
        enabled = enabled,
        label = { Text(text = label) },
        value = value, onValueChange = onChange,
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            focusedLabelColor = FocusColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.small,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = numberVisualTransformation
    )
}
