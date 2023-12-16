package com.example.soignemoi.feature.prescription.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.soignemoi.R
import com.example.soignemoi.data.model.Frequency
import com.example.soignemoi.data.model.Medicine
import com.example.soignemoi.feature.prescription.data.NewEntry

@Composable
fun AddEntryDialog(
    medicines: List<Medicine>,
    onAdd: (NewEntry) -> Unit,
    onDismiss: () -> Unit
) {

    var entry by remember { mutableStateOf(NewEntry()) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight(0.85f)) {
                MedicinePicker(
                    medicines = medicines,
                    onSelect = { entry = entry.copy(medicineId = it) }
                )
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp),
                        label = { Text(text = stringResource(id = R.string.dosage)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = entry.dosage,
                        onValueChange = { entry = entry.copy(dosage = it) }
                    )
                    BasicExposedDropDown(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp),
                        label = stringResource(id = R.string.frequency),
                        items = Frequency.all.map { BasicDropDownItem(it.id, it.title) },
                        text = Frequency.getFromId(entry.frequency).title,
                        onSelect = { entry = entry.copy(frequency = it) }
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    label = { Text(text = stringResource(id = R.string.instructions)) },
                    singleLine = false,
                    value = entry.note,
                    onValueChange = { entry = entry.copy(note = it) }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Button(modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 4.dp),
                        onClick = onDismiss) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp, end = 8.dp),
                        enabled = entry.isFilled,
                        onClick = { onAdd(entry) }) {
                        Text(text = stringResource(id = R.string.add))
                    }
                }
            }
        }
    }

}