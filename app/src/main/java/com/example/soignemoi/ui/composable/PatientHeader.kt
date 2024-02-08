package com.example.soignemoi.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.soignemoi.data.model.PatientData

@Composable
fun PatientHeader(patient: PatientData) = Column(modifier = Modifier.fillMaxWidth().testTag(PatientHeaderTag.HEADER)) {
    Text(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp).testTag(PatientHeaderTag.NAME),
        text = patient.getPatientFullName(),
        style = MaterialTheme.typography.headlineMedium
    )
    Text(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).testTag(PatientHeaderTag.REASON),
        text = patient.appointment.reason,
        style = MaterialTheme.typography.titleMedium
    )
}

fun PatientData?.getPatientFullName(): String {
    this?.let {
        return "${this.surname} ${this.name.uppercase()}"
    }
    return ""
}

object PatientHeaderTag {
    const val HEADER = "patient_header"
    const val NAME = "patient_name"
    const val REASON = "patient_reason"
}