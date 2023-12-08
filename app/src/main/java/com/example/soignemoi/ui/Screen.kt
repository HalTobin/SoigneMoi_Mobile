package com.example.soignemoi.ui

sealed class Screen(val route: String) {
    object Connection: Screen("connection")
    object PatientsList: Screen("patients")
    object PatientDetails: Screen("patient_details")
    object SeeNote: Screen("see_note")
    object AddNote: Screen("add_note")
    object Prescription: Screen("prescription")

}