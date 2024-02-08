package com.example.soignemoi.feature

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextInput
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.feature.add_note.presentation.AddNoteTag
import com.example.soignemoi.resource.PatientPlaceholder
import com.example.soignemoi.ui.AddNoteTestActivity
import com.example.soignemoi.ui.composable.PatientHeaderTag
import com.example.soignemoi.ui.composable.getPatientFullName
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkModule::class, RepositoryModule::class)
class AddNoteScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<AddNoteTestActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testAddNoteScreen() = runTest {
        val placeholder = PatientPlaceholder.patientDataResponsePlaceholder
        val placeholderNote = PatientPlaceholder.notesPlaceholder.first()
        composeRule.waitUntilExactlyOneExists(hasTestTag(PatientHeaderTag.HEADER))
        composeRule.onNodeWithTag(PatientHeaderTag.NAME).assertTextEquals(placeholder.getPatientFullName())
        composeRule.onNodeWithTag(PatientHeaderTag.REASON).assertTextEquals(placeholder.appointment.reason)
        composeRule.onNodeWithTag(AddNoteTag.TITLE).performTextInput(placeholderNote.title)
        composeRule.onNodeWithTag(AddNoteTag.CONTENT).performTextInput(placeholderNote.content)
        composeRule.waitForIdle()
        composeRule.onNodeWithTag(AddNoteTag.TITLE).assert(hasText(placeholderNote.title))
        composeRule.onNodeWithTag(AddNoteTag.CONTENT).assert(hasText(placeholderNote.content))
    }

}