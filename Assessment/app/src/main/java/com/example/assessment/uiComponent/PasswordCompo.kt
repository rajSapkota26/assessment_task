package com.example.assessment.uiComponent

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PasswordCompo(
    label: String = "Password",
    placeholderText: String = "Password",
    password: String,
    onPasswordChanged: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions { },
    paddingTop: Dp = 8.dp,
    paddingBottom: Dp = 8.dp,
    paddingStart: Dp = 8.dp,
    paddingEnd: Dp = 8.dp,
    isError:Boolean=false,
    supportingText:String=""
) {
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        supportingText= {Text(text = supportingText)  },
        isError=isError,
        onValueChange = { onPasswordChanged(it) },
        singleLine = true,
        placeholder = { Text(text = placeholderText) },
        visualTransformation =
        if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions,
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.VpnKey,
                    contentDescription = "password icon"
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                val image = if (passwordVisibility) {
                    Icons.Outlined.Visibility
                } else {
                    Icons.Outlined.VisibilityOff
                }
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        imageVector = image,
                        contentDescription = "password icon"
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingTop,
                start = paddingStart,
                end = paddingEnd,
                bottom = paddingBottom
            ),

//        colors = TextFieldDefaults.colors(
//            focusedPlaceholderColor = primary,
//            focusedTextColor = black,
//            unfocusedTextColor = black,
//            unfocusedPlaceholderColor = scrim,
//            disabledPlaceholderColor = scrim,
//            unfocusedContainerColor = white,
//            focusedContainerColor = white,
//            errorSupportingTextColor = Color.Red,
//            errorContainerColor = white,
//            errorTextColor = black
//        ),

        )

}


@Preview(showBackground = true)
@Composable
fun PasswordComporeview() {
    PasswordCompo(onPasswordChanged = {}, password = "")
}