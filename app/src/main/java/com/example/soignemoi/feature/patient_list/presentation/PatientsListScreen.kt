package com.example.soignemoi.feature.patient_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soignemoi.feature.patient_list.presentation.component.PatientItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientsListScreen(
    navController: NavController,
    state: PatientsListState,
    onEvent: (PatientsListEvent) -> Unit
) {

    Scaffold {
        LazyColumn() {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    text = "Mes patients".uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            itemsIndexed(state.patients) { index, patient ->
                PatientItem(modifier = Modifier, patient = patient)
                if (index < state.patients.lastIndex) HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth())
            }
        }
    }

}