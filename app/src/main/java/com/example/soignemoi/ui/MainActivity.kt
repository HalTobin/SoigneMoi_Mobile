package com.example.soignemoi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.soignemoi.feature.add_note.presentation.AddNoteScreen
import com.example.soignemoi.feature.add_note.presentation.AddNoteViewModel
import com.example.soignemoi.feature.login.presentation.LoginScreen
import com.example.soignemoi.feature.login.presentation.LoginViewModel
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsScreen
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsViewModel
import com.example.soignemoi.feature.patient_list.presentation.PatientsListScreen
import com.example.soignemoi.feature.patient_list.presentation.PatientsListViewModel
import com.example.soignemoi.feature.prescription.presentation.PrescriptionScreen
import com.example.soignemoi.feature.prescription.presentation.PrescriptionViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SoigneMoiTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Connection.route
                    ) {
                        composable(Screen.Connection.route) {
                            val viewModel = hiltViewModel<LoginViewModel>()
                            val state by viewModel.state.collectAsState()
                            LoginScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent,
                                uiEvent = viewModel.eventFlow
                            )
                        }
                        composable(Screen.PatientsList.route) {
                            val viewModel = hiltViewModel<PatientsListViewModel>()
                            val state by viewModel.state.collectAsState()
                            PatientsListScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(Screen.PatientDetails.route + "?patientId={patientId}",
                            arguments = listOf(navArgument(name = "patientId") {
                                type = NavType.IntType
                                defaultValue = -1
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
                        composable(Screen.AddNote.route + "?patientId={patientId}",
                            arguments = listOf(navArgument(name = "patientId") {
                                type = NavType.IntType
                                defaultValue = -1
                            })) {
                            val viewModel = hiltViewModel<AddNoteViewModel>()
                            val state by viewModel.state.collectAsState()
                            AddNoteScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent,
                                uiEvent = viewModel.eventFlow
                            )
                        }
                        composable(Screen.Prescription.route + "?appointmentId={appointmentId}&prescriptionId={prescriptionId}",
                            arguments = listOf(navArgument(name = "appointmentId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }, navArgument(name = "prescriptionId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                            )) {
                            val viewModel = hiltViewModel<PrescriptionViewModel>()
                            val state by viewModel.state.collectAsState()
                            PrescriptionScreen(
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
}