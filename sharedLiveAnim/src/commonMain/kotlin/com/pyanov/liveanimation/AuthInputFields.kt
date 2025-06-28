package com.pyanov.liveanimation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.pyanov.liveanimation.designSystem.SpacerSmall
import com.pyanov.liveanimation.designSystem.controlls.AppEditText

@Composable
fun AuthInputFields(
    modifier: Modifier,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле ввода логина
        AppEditText(
            modifier = Modifier.sizeIn(minWidth = 320.dp, maxWidth = 460.dp),
            placeholder = "Username",
            startText = username,
            imeAction = ImeAction.Next,
            onValueChange = { username = it }
        )

        SpacerSmall()

        // Поле ввода пароля
        AppEditText(
            modifier = Modifier.sizeIn(minWidth = 320.dp, maxWidth = 460.dp),
            placeholder = "Password",
            startText = password,
            keyboardType = KeyboardType.Password,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = { password = it },
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChange = onPasswordVisibilityChange
        )
    }
}