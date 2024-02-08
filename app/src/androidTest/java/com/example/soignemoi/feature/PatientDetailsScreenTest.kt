package com.example.soignemoi.feature

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsTag
import com.example.soignemoi.resource.PatientPlaceholder
import com.example.soignemoi.ui.PatientDetailsTestActivity
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
class PatientDetailsScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<PatientDetailsTestActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPatientDetailsScreen() = runTest {
        val placeholder = PatientPlaceholder.patientDataResponsePlaceholder
        composeRule.waitUntilExactlyOneExists(hasTestTag(PatientHeaderTag.HEADER))
        composeRule.onNodeWithTag(PatientHeaderTag.NAME).assertTextEquals(placeholder.getPatientFullName())
        composeRule.onNodeWithTag(PatientHeaderTag.REASON).assertTextEquals(placeholder.appointment.reason)
        composeRule.onAllNodesWithTag(PatientDetailsTag.NOTE).assertCountEquals(placeholder.notes.size)
        composeRule.onNodeWithTag(PatientDetailsTag.TAB_PRESCRIPTIONS).performClick()
        composeRule.waitUntilExactlyOneExists(hasTestTag(PatientDetailsTag.PAGE_PRESCRIPTION))
        composeRule.onAllNodesWithTag(PatientDetailsTag.PRESCRIPTION).assertCountEquals(placeholder.prescriptions.size)
        composeRule.onNodeWithTag(PatientDetailsTag.PAGE_PRESCRIPTION).performTouchInput { swipeRight() }
        composeRule.waitUntilExactlyOneExists(hasTestTag(PatientDetailsTag.PAGE_NOTES))
        composeRule.onAllNodesWithTag(PatientDetailsTag.NOTE).assertCountEquals(placeholder.notes.size)
    }

}