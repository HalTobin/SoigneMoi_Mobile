package com.example.soignemoi.feature.patient_details.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsState
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsTag
import com.example.soignemoi.ui.Screen

@Composable
fun PrescriptionPage(
    navController: NavController,
    modifier: Modifier = Modifier,
    state: PatientDetailsState
) {

    state.patientData?.let { data ->
        LazyColumn(
            modifier = modifier.fillMaxSize().testTag(PatientDetailsTag.PAGE_PRESCRIPTION),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(data.prescriptions.sortedByDescending { it.start }) { prescription ->
                Column(modifier = Modifier.testTag(PatientDetailsTag.PRESCRIPTION)) {
                    PrescriptionItem(prescription = prescription,
                        onSelect = {
                            navController.navigate(Screen.Prescription.route
                                    + "?appointmentId=${state.patientData.appointment.id}"
                                    + "&prescriptionId=${prescription.id}")
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}