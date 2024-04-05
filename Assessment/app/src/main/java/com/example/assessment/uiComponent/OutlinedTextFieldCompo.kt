package com.example.assessment.uiComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextFieldCompo(
    placeholderText: String,
    value: String,
    imageVector: ImageVector,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions { },
    keyboardCapitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    paddingTop: Dp = 8.dp,
    paddingBottom: Dp = 8.dp,
    paddingStart: Dp = 8.dp,
    paddingEnd: Dp = 8.dp,
    enable: Boolean = true,
    leadingIcon: Boolean = true,
    isError:Boolean=false,
    supportingText:String=""
) {

    OutlinedTextField(
//        label = { Text(text = placeholderText)},
        value = value,
        supportingText= {Text(text = supportingText)  },
        isError=isError,
        onValueChange = { onValueChanged(it) },
        placeholder = { Text(text = placeholderText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = keyboardCapitalization,
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        leadingIcon = {
            if (leadingIcon) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "icon",
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

        keyboardActions = keyboardActions,
        enabled = enable,

    )

}


@Composable
@Preview
fun previewCurrentComponent() {
    CommonCard {
        Column {


        }

    }
}

