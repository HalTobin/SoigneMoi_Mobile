package com.example.soignemoi.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.soignemoi.data.api.SessionManagerTest
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.feature.add_note.presentation.AddNoteTag
import com.example.soignemoi.feature.login.presentation.LoginScreenTag
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsTag
import com.example.soignemoi.feature.patient_list.presentation.PatientsListTag
import com.example.soignemoi.feature.prescription.presentation.PrescriptionScreenTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkModule::class, RepositoryModule::class)
class MainActivityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNavigation() = runTest {
        composeRule.awaitIdle()
        composeRule.onNodeWithTag(LoginScreenTag.SCREEN).assertIsDisplayed()
        composeRule.onNodeWithTag(LoginScreenTag.CONNECTION).performClick()
        composeRule.awaitIdle()
        composeRule.waitUntilExactlyOneExists(hasTestTag(PatientsListTag.SCREEN))
        composeRule.waitUntilAtLeastOneExists(hasTestTag(PatientsListTag.PATIENT_ITEM_CONTAINER))
        composeRule.onAllNodesWithTag(PatientsListTag.PATIENT_ITEM_CONTAINER).onFirst().performClick()
        composeRule.awaitIdle()
        composeRule.waitUntilExactlyOneExists(hasTestTag(PatientDetailsTag.SCREEN))
        composeRule.onNodeWithTag(PatientDetailsTag.PAGE_NOTES).assertExists()
        composeRule.waitUntilAtLeastOneExists(hasTestTag(PatientDetailsTag.NOTE))
        composeRule.onNodeWithTag(PatientDetailsTag.ADD).performClick()
        composeRule.awaitIdle()
        composeRule.waitUntilExactlyOneExists(hasTestTag(AddNoteTag.SCREEN))
        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.waitUntilDoesNotExist(hasTestTag(AddNoteTag.SCREEN))
        composeRule.awaitIdle()
        composeRule.onNodeWithTag(PatientDetailsTag.TAB_PRESCRIPTIONS).performClick()
        composeRule.onNodeWithTag(PatientDetailsTag.PAGE_PRESCRIPTION).assertExists()
        composeRule.onNodeWithTag(PatientDetailsTag.ADD).performClick()
        composeRule.awaitIdle()
        composeRule.waitUntilExactlyOneExists(hasTestTag(PrescriptionScreenTag.SCREEN))
        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.waitUntilDoesNotExist(hasTestTag(PrescriptionScreenTag.SCREEN))
        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.awaitIdle()
        composeRule.waitUntilDoesNotExist(hasTestTag(PatientDetailsTag.SCREEN))
    }

}