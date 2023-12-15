package com.example.soignemoi.feature.patient_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soignemoi.feature.patient_list.presentation.component.LoadingPatientItem
import com.example.soignemoi.feature.patient_list.presentation.component.PatientItem
import com.example.soignemoi.ui.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientsListScreen(
    navController: NavController,
    state: PatientsListState,
    onEvent: (PatientsListEvent) -> Unit
) {

    Scaffold {
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Mes patients".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { onEvent(PatientsListEvent.Refresh) }) {
                    Icon(modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Refresh, contentDescription = null)
                }
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(top = 4.dp))
            LazyColumn {
                if (!state.loading) itemsIndexed(state.patients) { index, patient ->
                    Column(modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.PatientDetails.route + "?patientId=${patient.id}")
                        }) {
                        PatientItem(patient = patient)
                        if (index < state.patients.lastIndex) HorizontalDivider(modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth())
                    }
                } else items(4) { LoadingPatientItem() }
            }
        }
    }

}