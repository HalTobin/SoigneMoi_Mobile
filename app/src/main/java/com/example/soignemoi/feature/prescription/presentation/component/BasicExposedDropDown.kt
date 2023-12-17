package com.example.soignemoi.feature.prescription.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicExposedDropDown(
    modifier: Modifier,
    label: String,
    items: List<BasicDropDownItem>,
    text: String,
    onSelect: (Int) -> Unit,
) {

    var isOpen by remember { mutableStateOf(false) }

    Box(modifier = modifier) {

        ExposedDropdownMenuBox(
            expanded = isOpen,
            onExpandedChange = { isOpen = !isOpen }
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                label = { Text(text = label) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = text,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isOpen) },
                onValueChange = { }
            )

            ExposedDropdownMenu(
                expanded = isOpen,
                onDismissRequest = { isOpen = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item.title) },
                        onClick = {
                            isOpen = false
                            onSelect(item.id)
                        }
                    )
                }
            }

        }
    }
}

data class BasicDropDownItem(val id: Int, val title: String)