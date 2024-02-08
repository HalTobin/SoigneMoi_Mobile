package com.example.soignemoi.feature.add_note.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soignemoi.R
import com.example.soignemoi.ui.composable.PatientHeader
import com.example.soignemoi.util.Constants.MAX_CHAR
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddNoteScreen(
    navController: NavController,
    state: AddNoteState,
    onEvent: (AddNoteEvent) -> Unit,
    uiEvent: SharedFlow<AddNoteViewModel.UiEvent>
) {

    LaunchedEffect(key1 = true) {
        uiEvent.collectLatest { event ->
            when (event) {
                is AddNoteViewModel.UiEvent.QuitAddNoteScreen -> navController.popBackStack()
            }
        }
    }

    Scaffold(modifier = Modifier.testTag(AddNoteTag.SCREEN)) {
        state.patientData?.let {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                PatientHeader(patient = state.patientData)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.new_note).uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .testTag(AddNoteTag.TITLE),
                    label = { Text(text = stringResource(id = R.string.note_title)) },
                    value = state.title,
                    singleLine = true,
                    onValueChange = { onEvent(AddNoteEvent.UpdateTitle(it)) }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .testTag(AddNoteTag.CONTENT),
                    label = { Text(text = stringResource(id = R.string.note_content)) },
                    value = state.content,
                    onValueChange = { if (it.length < MAX_CHAR) onEvent(AddNoteEvent.UpdateContent(it)) },
                    supportingText = {
                        Text(
                            text = "${state.content.length} / $MAX_CHAR",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                        )
                    },
                )
                Button(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    onClick = { onEvent(AddNoteEvent.SaveNote) }) {
                    Text(text = stringResource(id = R.string.save).uppercase())
                }
            }
        }
    }

}

object AddNoteTag {
    const val SCREEN = "add_note_screen"
    const val TITLE = "add_note_title"
    const val CONTENT = "add_note_content"
}