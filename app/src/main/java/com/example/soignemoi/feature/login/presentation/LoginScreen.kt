package com.example.soignemoi.feature.login.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.soignemoi.R
import com.example.soignemoi.ui.Screen
import com.example.soignemoi.ui.theme.DarkRed
import com.example.soignemoi.ui.theme.LightRed
import com.example.soignemoi.ui.theme.SoigneMoiTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    navController: NavController,
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    uiEvent: SharedFlow<LoginViewModel.UiEvent>
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is LoginViewModel.UiEvent.ConnectionSuccess -> navController.navigate(Screen.PatientsList.route)
            }
        }
    }

    Scaffold(modifier = Modifier.testTag(LoginScreenTag.SCREEN)) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.doctors_app),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(0.75f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().testTag(LoginScreenTag.MAIL),
                    label = { Text(text = stringResource(id = R.string.username)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters
                    ),
                    value = state.username,
                    onValueChange = { onEvent(LoginEvent.ChangeField(LoginField.USERNAME, it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().testTag(LoginScreenTag.PASSWORD),
                    label = { Text(text = stringResource(id = R.string.password)) },
                    singleLine = true,
                    visualTransformation =  if (state.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Password
                    ),
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.testTag(LoginScreenTag.SHOW_PASSWORD),
                            onClick = { onEvent(LoginEvent.InvertShowPassword) }) {
                            Icon(
                                imageVector = if (state.showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.ChangeField(LoginField.PASSWORD, it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        modifier = Modifier.testTag(LoginScreenTag.SAVE_CREDENTIALS),
                        checked = state.saveLogin,
                        onCheckedChange = { onEvent(LoginEvent.ChangeSavedLogin(it)) }
                    )
                    Text(modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.remember_login))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(0.6f).testTag(LoginScreenTag.CONNECTION),
                    onClick = { onEvent(LoginEvent.Connect) }) {
                    Text(text = stringResource(id = R.string.connection).uppercase())
                }
            }
            if (state.error) Box(modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(2.dp, DarkRed, RoundedCornerShape(8.dp))
                .background(LightRed),
                contentAlignment = Alignment.Center
            ) {
                Text(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    text = stringResource(id = R.string.error_occured))
            }
        }
    }

}

object LoginScreenTag {
    const val SCREEN = "login_screen"
    const val MAIL = "login_mail_field"
    const val PASSWORD = "login_password_field"
    const val SAVE_CREDENTIALS = "login_save_field"
    const val SHOW_PASSWORD = "login_show_password_button"
    const val CONNECTION = "login_connection_button"
}

@Preview
@Composable
fun PreviewLoginScreen() = SoigneMoiTheme {
    LoginScreen(
        navController = rememberNavController(),
        state = LoginState(),
        onEvent = {},
        uiEvent = MutableSharedFlow<LoginViewModel.UiEvent>().asSharedFlow()
    )
}