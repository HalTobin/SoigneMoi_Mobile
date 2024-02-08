package com.example.soignemoi.feature.patient_details.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soignemoi.feature.patient_details.presentation.PatientDetailsTab.Companion.TAB_ITEMS
import com.example.soignemoi.feature.patient_details.presentation.components.NotePage
import com.example.soignemoi.feature.patient_details.presentation.components.PrescriptionPage
import com.example.soignemoi.ui.Screen
import com.example.soignemoi.ui.composable.PatientHeader
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientDetailsScreen(
    navController: NavController,
    state: PatientDetailsState,
    onEvent: (PatientDetailsEvent) -> Unit
) {

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState { TAB_ITEMS.size }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.testTag(PatientDetailsTag.SCREEN),
        floatingActionButton = {
            SmallFloatingActionButton(
                modifier = Modifier.size(64.dp).testTag(PatientDetailsTag.ADD),
                onClick = {
                    if (selectedTabIndex == PatientDetailsTab.Notes.id) navController.navigate(Screen.AddNote.route + "?patientId=${state.patientId}")
                    if (selectedTabIndex == PatientDetailsTab.Prescriptions.id) navController.navigate(Screen.Prescription.route + "?appointmentId=${state.patientData?.appointment?.id ?: -1}")
                          },
            ) {
                Icon(modifier = Modifier.size(24.dp),
                    imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) {
        state.patientData?.let {
            Column(modifier = Modifier.fillMaxWidth()) {
                PatientHeader(patient = state.patientData)
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                TabRow(selectedTabIndex = selectedTabIndex) {
                    TAB_ITEMS.forEach() { item ->
                        Tab(
                            modifier = Modifier.testTag(item.tag),
                            selected = (item.id == selectedTabIndex),
                            onClick = {
                                selectedTabIndex = item.id
                                scope.launch { pagerState.animateScrollToPage(selectedTabIndex) }
                                      },
                            text = { Text(item.title.uppercase()) }
                        )
                    }
                }
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    userScrollEnabled = true,
                    state = pagerState) { index ->
                    when (index) {
                        PatientDetailsTab.Notes.id -> NotePage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            state = state
                        )
                        PatientDetailsTab.Prescriptions.id -> PrescriptionPage(
                            navController = navController,
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            state = state
                        )
                    }
                }
            }
        }
    }

}

object PatientDetailsTag {
    const val SCREEN = "patient_details_screen"
    const val ADD = "patient_details_add"
    const val TAB_NOTES = "patient_details_tab_notes"
    const val TAB_PRESCRIPTIONS = "patient_details_tab_prescription"
    const val PAGE_NOTES = "patient_details_page_notes"
    const val PAGE_PRESCRIPTION = "patient_details_page_prescription"
    const val NOTE = "patient_details_note_item"
    const val PRESCRIPTION = "patient_details_prescription_item"
}

sealed class PatientDetailsTab(val title: String, val id: Int, val tag: String) {
    object Notes: PatientDetailsTab(title = "Notes", id = 0, tag = PatientDetailsTag.TAB_NOTES)
    object Prescriptions: PatientDetailsTab(title = "Prescriptions", id = 1, tag = PatientDetailsTag.TAB_PRESCRIPTIONS)

    companion object {
        val TAB_ITEMS: List<PatientDetailsTab> = listOf(Notes, Prescriptions)
    }
}