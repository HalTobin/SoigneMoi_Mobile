package com.example.soignemoi.feature.patient_details.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsState

@Composable
fun NotePage(
    modifier: Modifier = Modifier,
    state: PatientDetailsState,
) {

    state.patientData?.let { data ->
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(data.notes.sortedByDescending { it.date }) { note ->
                Column {
                    NoteItem(note = note)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}