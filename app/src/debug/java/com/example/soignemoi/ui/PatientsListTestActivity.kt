package com.example.soignemoi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soignemoi.feature.patient_list.presentation.PatientsListScreen
import com.example.soignemoi.feature.patient_list.presentation.PatientsListViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientsListTestActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SoigneMoiTheme {
                NavHost(navController = navController, startDestination = Screen.PatientsList.route) {
                    composable(route = Screen.PatientsList.route) {
                        val viewModel = hiltViewModel<PatientsListViewModel>()
                        val state by viewModel.state.collectAsState()
                        PatientsListScreen(
                            navController = navController,
                            state = state,
                            onEvent = viewModel::onEvent,
                        )
                    }
                }
            }
        }
    }

}