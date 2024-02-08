package com.example.soignemoi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.soignemoi.feature.prescription.presentation.PrescriptionScreen
import com.example.soignemoi.feature.prescription.presentation.PrescriptionViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrescriptionTestActivity: ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SoigneMoiTheme {
                NavHost(navController = navController, startDestination = Screen.Prescription.route) {
                    composable(route = Screen.Prescription.route + "?appointmentId={appointmentId}&prescriptionId={prescriptionId}",
                        arguments = listOf(navArgument(name = "appointmentId") {
                            type = NavType.IntType
                            defaultValue = 1
                        }, navArgument(name = "prescriptionId") {
                            type = NavType.IntType
                            defaultValue = 1
                        }
                        )
                    ) {
                        val viewModel = hiltViewModel<PrescriptionViewModel>()
                        val state by viewModel.state.collectAsState()
                        PrescriptionScreen(
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