package com.example.assessment.uiComponent

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun CheckBoxCompo(
    isChecked: Boolean,
    label: String,
    onCheck: (Boolean) -> Unit,
) {

    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                Timber.v(it.toString())
                onCheck(it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Text(
            text = label,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(end = 16.dp)
        )


    }


}

@Preview(showBackground = true)
@Composable
fun CheckBoxCompoPreview() {
    CheckBoxCompo(
        label = "Accept our Trems And Condition",
        onCheck = {

        }, isChecked = true
    )
}
