package com.example.soignemoi.feature.patient_list.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.soignemoi.data.model.Patient

@Composable
fun PatientItem(
    modifier: Modifier,
    patient: Patient
) {

    Row(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier.weight(1f)) {
            Text(modifier = Modifier.padding(bottom = 2.dp),
                text = "${patient.surname} ${patient.name.uppercase()}", fontWeight = FontWeight.SemiBold)
            Text(text = patient.reason)
        }
        Icon(modifier = Modifier.size(32.dp),
            imageVector = Icons.Outlined.Info, contentDescription = "Informations sur le patient")
        Spacer(modifier = Modifier.width(8.dp))
    }

}