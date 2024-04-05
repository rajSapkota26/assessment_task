package com.example.assessment.uiComponent

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.assessment.ui.theme.grey


@Preview(showBackground = true)
@Composable
fun DDPreview() {
    DropDownMenuCompo(
        overrideText = "Pick One",
        label = "Pick One",
        menuItems = arrayListOf("item one", "item two"),
        onSelectItem = { _, _ -> })
}

@Composable
fun DropDownMenuCompo(
    label: String,
    menuItems: ArrayList<String>?,
    enableTextField: Boolean = false,
    readOnlyTextField: Boolean = false, // false to edit on textfield otherwise true (readonly i.e. will not allow typing)
    onSelectItem: (Int, String) -> Unit,
    overrideText: String? = null,
    textFieldValueChange: (String) -> Unit = {},
) {

    var expanded by remember { mutableStateOf(false) }

    var selectedText by remember { mutableStateOf(label) }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown


    Column(
        Modifier
            .padding(8.dp)
            .border(1.dp, grey, RoundedCornerShape(4.dp)),
        verticalArrangement = Arrangement.Center

    ) {
//    Column(Modifier.padding(top=8.dp)) {
        OutlinedTextField(
            value = if (overrideText.isNullOrEmpty()) selectedText else overrideText,
            onValueChange = {
                selectedText = it
                textFieldValueChange(it)
            },
            enabled = enableTextField,
            readOnly = readOnlyTextField,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(4.dp))
                .clickable {
                    expanded = !expanded
                }
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier
                        .clickable { expanded = !expanded }
                        .size(24.dp)
                )
            },
            leadingIcon = {
                Icon(Icons.Filled.Tag, "contentDescription",
                    Modifier
                        .clickable { expanded = !expanded }
                        .size(24.dp)
                )
            },
            textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.scrim),

            )


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
//                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                .width(with(LocalDensity.current) {
                    textfieldSize.width.toDp()
                }
                ),
        ) {
            if (!menuItems.isNullOrEmpty()) {
                menuItems.forEachIndexed { index, label ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        onSelectItem(index, label)
                    }, text = {
                        Row() {
                            Text(text = label)
                        }
                    })
                }
            }
        }


    }

}

