package com.example.assessment.uiComponent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilledButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary

        )
    ) {
        Text(label)
    }
}

@Composable
fun FilledTButton(label: String, onClick: () -> Unit) {

    FilledTonalButton(
        modifier=Modifier.fillMaxWidth(),
        onClick = { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary

        )
    ) {
        Text(label)
    }
}



@Composable
@Preview
fun previewCurrentButtonComponent() {
    CommonCard {
        Column {
            FilledButton("filled button") {}
            FilledTButton("filled Tonal button") {}
        }

    }
}