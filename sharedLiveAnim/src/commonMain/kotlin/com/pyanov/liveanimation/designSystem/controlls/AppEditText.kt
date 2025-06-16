package com.pyanov.liveanimation.designSystem.controlls

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.pyanov.liveanimation.EMPTY_STRING
import com.pyanov.liveanimation.designSystem.LATheme
import com.pyanov.liveanimation.designSystem.getDefaultShape

private val mainColor = Color(0xFF263063)

@Composable
fun AppEditText(
    modifier: Modifier = Modifier,
    placeholder: String = EMPTY_STRING,
    startText: String = EMPTY_STRING,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityChange: ((Boolean) -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    var text by remember { mutableStateOf(startText) }

    OutlinedTextField(
        modifier = modifier,
        placeholder = { makeLabel(placeholder) },
        value = text,
        onValueChange = {
            text = it
            onValueChange.invoke(text)
        },
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        shape = getDefaultShape(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = mainColor,//LATheme.colors.body,
            unfocusedTextColor = mainColor,

            focusedContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

            focusedTrailingIconColor = mainColor,
            unfocusedTrailingIconColor = mainColor,

            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,

            focusedPlaceholderColor = mainColor.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = mainColor.copy(alpha = 0.6f),

            cursorColor = mainColor
        ),
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (onPasswordVisibilityChange != null) {
                IconButton(onClick = { onPasswordVisibilityChange(!isPasswordVisible) }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                    )
                }
            }
        }
    )
}

@Composable
private fun makeLabel(label: String) {
    if (label.trim().isNotEmpty()) {
        Text(text = label)
    }
}