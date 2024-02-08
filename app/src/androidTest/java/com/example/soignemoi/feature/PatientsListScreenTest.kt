package com.example.soignemoi.feature

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.example.soignemoi.di.NetworkModule
import com.example.soignemoi.di.RepositoryModule
import com.example.soignemoi.di.ServiceModule
import com.example.soignemoi.feature.patient_list.presentation.PatientsListTag
import com.example.soignemoi.resource.PatientPlaceholder
import com.example.soignemoi.ui.PatientsListTestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkModule::class, RepositoryModule::class)
class PatientsListScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<PatientsListTestActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testPatientsListScreen() = runTest {
        composeRule.waitUntilAtLeastOneExists(hasTestTag(PatientsListTag.PATIENT_ITEM_CONTAINER))
        val expectedNodes = PatientPlaceholder.patientsResponsePlaceholder.size
        composeRule.onAllNodesWithTag(PatientsListTag.PATIENT_ITEM_CONTAINER).assertCountEquals(expectedNodes)
    }

}