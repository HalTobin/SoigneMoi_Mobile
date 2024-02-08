package com.example.soignemoi.feature.prescription

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import com.example.soignemoi.R
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.feature.prescription.presentation.PrescriptionScreenTag
import com.example.soignemoi.resource.PatientPlaceholder
import com.example.soignemoi.ui.PrescriptionTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkModule::class, RepositoryModule::class)
class PrescriptionScreenTest {

    private lateinit var context: Context

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<PrescriptionTestActivity>()

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPrescriptionScreen() = runTest {
        val placeholder = PatientPlaceholder.patientDataResponsePlaceholder
        val placeholderPrescription = placeholder.prescriptions.first()
        composeRule.waitUntilExactlyOneExists(hasTestTag(PrescriptionScreenTag.TITLE))
        composeRule.onNodeWithTag(PrescriptionScreenTag.ADD_ENTRY).assertDoesNotExist()
        val expectedTitle = context.resources.getString(R.string.prescription_n, placeholderPrescription.id).uppercase()
        composeRule.onNodeWithTag(PrescriptionScreenTag.TITLE).assertTextEquals(expectedTitle)
        composeRule.onAllNodesWithTag(PrescriptionScreenTag.ENTRY).assertCountEquals(placeholderPrescription.entries.size)
    }

}