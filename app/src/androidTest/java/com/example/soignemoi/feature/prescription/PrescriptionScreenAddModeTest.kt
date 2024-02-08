package com.example.soignemoi.feature.prescription

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.example.soignemoi.R
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.feature.prescription.presentation.PrescriptionScreenTag
import com.example.soignemoi.feature.prescription.presentation.component.AddEntryDialogTag
import com.example.soignemoi.resource.MedicinePlaceholder
import com.example.soignemoi.resource.PatientPlaceholder
import com.example.soignemoi.ui.PrescriptionAddModeTestActivity
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
class PrescriptionScreenAddModeTest {

    private lateinit var context: Context

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<PrescriptionAddModeTestActivity>()

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPrescriptionAddModeScreen() = runTest {
        val placeholder = PatientPlaceholder.patientDataResponsePlaceholder
        val medicines = MedicinePlaceholder.medicineResponsePlaceholder
        val entry = placeholder.prescriptions.first().entries.first()

        composeRule.waitUntilExactlyOneExists(hasTestTag(PrescriptionScreenTag.TITLE))
        val addEntry = composeRule.onNodeWithTag(PrescriptionScreenTag.ADD_ENTRY)
        addEntry.assertExists()
        val expectedTitle = context.resources.getString(R.string.new_prescription).uppercase()
        composeRule.onNodeWithTag(PrescriptionScreenTag.TITLE).assertTextEquals(expectedTitle)
        val entries = composeRule.onAllNodesWithTag(PrescriptionScreenTag.ENTRY)
        entries.assertCountEquals(0)

        addEntry.performClick()
        composeRule.awaitIdle()
        composeRule.onNodeWithTag(AddEntryDialogTag.SELECT_MEDICINE).performClick()
        composeRule.awaitIdle()
        composeRule.onAllNodesWithTag(AddEntryDialogTag.MEDICINE_ITEM).assertCountEquals(medicines.size)

        composeRule.onNodeWithTag(AddEntryDialogTag.MEDICINE_SEARCH).performTextInput(entry.medicine.title)
        composeRule.awaitIdle()
        composeRule.waitUntilExactlyOneExists(hasTestTag(AddEntryDialogTag.MEDICINE_ITEM))
        //composeRule.onNodeWithTag(AddEntryDialogTag.MEDICINE_TITLE).assertTextEquals(entry.medicine.title)
        composeRule.onNodeWithTag(AddEntryDialogTag.MEDICINE_ITEM).performClick()

        composeRule.awaitIdle()
        composeRule.onNodeWithTag(AddEntryDialogTag.SELECT_MEDICINE).assert(hasText(entry.medicine.title))
    }

}