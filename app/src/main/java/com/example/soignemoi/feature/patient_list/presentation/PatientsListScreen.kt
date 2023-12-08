package com.example.soignemoi.feature.patient_list.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientsListScreen(
    navController: NavController,
    state: PatientsListState,
    onEvent: (PatientsListEvent) -> Unit
) {

    Scaffold {
        // TODO - Screen content
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Text(text = "PatientsListScreen")
        }
    }

}