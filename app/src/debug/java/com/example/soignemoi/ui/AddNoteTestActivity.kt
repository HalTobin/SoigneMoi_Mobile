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
import com.example.soignemoi.feature.add_note.presentation.AddNoteScreen
import com.example.soignemoi.feature.add_note.presentation.AddNoteViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteTestActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SoigneMoiTheme {
                NavHost(navController = navController, startDestination = Screen.AddNote.route) {
                    composable(route = Screen.AddNote.route + "?patientId={patientId}",
                        arguments = listOf(navArgument(name = "patientId") {
                            type = NavType.IntType
                            defaultValue = 1
                        })
                    ) {
                        val viewModel = hiltViewModel<AddNoteViewModel>()
                        val state by viewModel.state.collectAsState()
                        AddNoteScreen(
                            navController = navController,
                            state = state,
                            onEvent = viewModel::onEvent,
                            uiEvent = viewModel.eventFlow
                        )
                    }
                }
            }
        }
    }

}