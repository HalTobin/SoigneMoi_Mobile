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
import com.example.soignemoi.feature.login.presentation.LoginScreen
import com.example.soignemoi.feature.login.presentation.LoginViewModel
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginTestActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SoigneMoiTheme {
                NavHost(navController = navController, startDestination = Screen.Connection.route) {
                    composable(route = Screen.Connection.route) {
                        val viewModel = hiltViewModel<LoginViewModel>()
                        val state by viewModel.state.collectAsState()
                        LoginScreen(
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