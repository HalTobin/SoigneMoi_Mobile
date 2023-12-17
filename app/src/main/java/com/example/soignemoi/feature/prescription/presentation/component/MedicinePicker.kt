package com.example.soignemoi.feature.prescription.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.soignemoi.R
import com.example.soignemoi.data.model.Medicine

@Composable
fun MedicinePicker(
    medicines: List<Medicine>,
    onSelect: (Int) -> Unit,
) {
    var isOpen by remember { mutableStateOf(false) }

    var search by remember { mutableStateOf("") }

    var selected by remember { mutableStateOf<Int?>(null) }

    if (isOpen) Dialog(onDismissRequest = { isOpen = false }) {
        Surface(shape = RoundedCornerShape(8.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.search))},
                    value = search,
                    onValueChange = { search = it }
                )
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    itemsIndexed(medicines.filter { if (search.isNotBlank()) it.title.lowercase().contains(search.lowercase()) else true }) { index, medicine ->
                        Column(modifier = Modifier.clickable {
                            selected = medicine.id
                            isOpen = false
                            onSelect(medicine.id)
                        }) {
                            Text(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
                                style = MaterialTheme.typography.titleMedium,
                                text = medicine.title)
                            if (index < medicines.lastIndex) HorizontalDivider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isOpen = true },
        readOnly = true,
        enabled = false,
        label = { Text(text = stringResource(id = R.string.medicine)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value =
        if (selected == null) ""
        else medicines.find { selected == it.id }?.title ?: stringResource(id = R.string.error),
        trailingIcon = {
            IconButton(onClick = { isOpen = true }) {
                Icon(
                    imageVector = if (!isOpen) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
                    contentDescription = null
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            disabledBorderColor = OutlinedTextFieldDefaults.colors().unfocusedTrailingIconColor,
            disabledTextColor = OutlinedTextFieldDefaults.colors().unfocusedTextColor,
            disabledLabelColor = OutlinedTextFieldDefaults.colors().unfocusedLabelColor,
            disabledTrailingIconColor = OutlinedTextFieldDefaults.colors().unfocusedTrailingIconColor
        ),
        onValueChange = { }
    )

}