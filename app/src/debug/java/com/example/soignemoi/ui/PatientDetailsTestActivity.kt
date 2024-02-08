package com.example.soignemoi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsScreen
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientDetailsTestActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SoigneMoiTheme {
                NavHost(navController = navController, startDestination = Screen.PatientDetails.route) {
                    composable(Screen.PatientDetails.route + "?patientId={patientId}",
                        arguments = listOf(navArgument(name = "patientId") {
                            type = NavType.IntType
                            defaultValue = 1
                        })
                    ) {
                        val viewModel = hiltViewModel<PatientDetailsViewModel>()
                        val state by viewModel.state.collectAsState()
                        PatientDetailsScreen(
                            navController = navController,
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            }
        }
    }

}