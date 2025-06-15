package com.pyanov.liveanimation.designSystem.controlls

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.pyanov.liveanimation.EMPTY_STRING
import com.pyanov.liveanimation.designSystem.LATheme
import com.pyanov.liveanimation.designSystem.getDefaultShape

@Composable
fun AppEditText(
    modifier: Modifier = Modifier,
    startLabel: String = EMPTY_STRING,
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
    val label by remember { mutableStateOf(startLabel.trim()) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
            onValueChange.invoke(text)
        },
        label = { makeLabel(label) },
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
            imeAction = imeAction,
        ),
        shape = getDefaultShape(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = LATheme.colors.body,
            focusedBorderColor = LATheme.colors.primary,
            unfocusedBorderColor = LATheme.colors.primary,
        ),
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (onPasswordVisibilityChange != null) {
                IconButton(onClick = { onPasswordVisibilityChange(!isPasswordVisible) }) {
                    Icon(
                        imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = LATheme.colors.body
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