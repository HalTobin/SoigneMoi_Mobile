package com.example.soignemoi.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.example.soignemoi.feature.see_note.presentation.SeeNoteScreen
import com.example.soignemoi.feature.see_note.presentation.SeeNoteViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                                onEvent = viewModel::onEvent
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
                        composable(Screen.PatientDetails.route) {
                            val viewModel = hiltViewModel<PatientDetailsViewModel>()
                            val state by viewModel.state.collectAsState()
                            PatientDetailsScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(Screen.SeeNote.route) {
                            val viewModel = hiltViewModel<SeeNoteViewModel>()
                            val state by viewModel.state.collectAsState()
                            SeeNoteScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(Screen.AddNote.route) {
                            val viewModel = hiltViewModel<AddNoteViewModel>()
                            val state by viewModel.state.collectAsState()
                            AddNoteScreen(
                                navController = navController,
                                state = state,
                                onEvent = viewModel::onEvent
                            )
                        }
                        composable(Screen.Prescription.route) {
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