package com.example.soignemoi.feature.prescription.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soignemoi.R
import com.example.soignemoi.feature.prescription.presentation.component.AddEntryDialog
import com.example.soignemoi.feature.prescription.presentation.component.MyDatePickerDialog
import com.example.soignemoi.feature.prescription.presentation.component.PrescriptionEntryItem
import com.example.soignemoi.feature.prescription.presentation.component.getTimestampForMidnight
import com.example.soignemoi.ui.composable.PatientHeader
import java.text.SimpleDateFormat
import java.util.Date

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrescriptionScreen(
    navController: NavController,
    state: PrescriptionState,
    onEvent: (PrescriptionEvent) -> Unit
) {

    var isStartDatePickerOpen by remember { mutableStateOf(false) }
    var isEndDatePickerOpen by remember { mutableStateOf(false) }
    var isAddEntryOpen by remember { mutableStateOf(false) }

    val isEditable = !((state.prescriptionId != null) && (state.dateEnd?.time ?: -1) < System.currentTimeMillis())

    Scaffold(modifier = Modifier.testTag(PrescriptionScreenTag.SCREEN)) {

        if (isStartDatePickerOpen) MyDatePickerDialog(
            maximumInMillis = state.dateEnd?.time ?: Long.MAX_VALUE,
            onDateSelected = {
                onEvent(PrescriptionEvent.SelectStartDate(it))
                isStartDatePickerOpen = false
            },
            onDismiss = { isStartDatePickerOpen = false }
        )

        if (isEndDatePickerOpen) MyDatePickerDialog(
            minimumInMillis = state.dateStart?.time ?: getTimestampForMidnight(),
            onDateSelected = {
                onEvent(PrescriptionEvent.SelectEndDate(it))
                isEndDatePickerOpen = false
            },
            onDismiss = { isEndDatePickerOpen = false }
        )

        if (isAddEntryOpen) AddEntryDialog(
            medicines = state.medicines,
            onAdd = {
                isAddEntryOpen = false
                onEvent(PrescriptionEvent.AddEntry(it))
            },
            onDismiss = { isAddEntryOpen = false }
        )

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .testTag(PrescriptionScreenTag.TITLE),
                text =
                    if (state.prescriptionId == null) stringResource(id = R.string.new_prescription).uppercase()
                    else stringResource(id = R.string.prescription_n, state.prescriptionId).uppercase(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(state.entries) { entry ->
                    PrescriptionEntryItem(
                        medicines = state.medicines,
                        entry = entry
                    )
                }
                if (state.prescriptionId == null) item {
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedButton(
                            modifier = Modifier.testTag(PrescriptionScreenTag.ADD_ENTRY),
                            onClick = { isAddEntryOpen = true }) {
                            Icon(Icons.Default.Add, null)
                            Text(text = stringResource(id = R.string.add_medicine))
                        }
                    }
                }
            }
            Row {
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 4.dp)
                    .testTag(PrescriptionScreenTag.START_DATE),
                    enabled = (state.prescriptionId == null),
                    onClick = { isStartDatePickerOpen = true }) {
                    Text(modifier = Modifier.padding(start = 4.dp),
                        text =
                        if (state.dateStart == null) stringResource(id = R.string.date_start)
                        else state.startFormatted
                    )
                }
                Button(modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp, end = 8.dp)
                    .testTag(PrescriptionScreenTag.END_DATE),
                    enabled = isEditable,
                    onClick = { isEndDatePickerOpen = true }) {
                    Text(text =
                        if (state.dateEnd == null) stringResource(id = R.string.date_end)
                        else state.endFormatted
                    )
                }
            }
            if (isEditable) Button(modifier = Modifier
                .fillMaxWidth(1f)
                .padding(start = 4.dp, end = 8.dp),
                enabled = state.canSave(),
                onClick = {
                    onEvent(PrescriptionEvent.Save)
                    navController.popBackStack()
                }) {
                Text(text = stringResource(id = R.string.save).uppercase())
        }

    }

}

}

object PrescriptionScreenTag {
    const val SCREEN = "prescription_screen"
    const val TITLE = "prescription_title"
    const val ADD_ENTRY = "prescription_add_entry"
    const val ENTRY = "prescription_entry"
    const val START_DATE = "prescription_entry_start"
    const val END_DATE = "prescription_entry_end"
}