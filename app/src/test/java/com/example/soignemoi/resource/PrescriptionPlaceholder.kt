package com.example.soignemoi.resource

import com.example.soignemoi.feature.prescription.data.NewEntry
import com.example.soignemoi.feature.prescription.data.NewPrescription
import com.example.soignemoi.resource.ConstPlaceholder.END_DATE_STR
import com.example.soignemoi.resource.ConstPlaceholder.START_DATE_STR

object PrescriptionPlaceholder {

    val newPrescription = NewPrescription(
        id = 1,
        appointmentId = 1,
        start = START_DATE_STR,
        end = END_DATE_STR,
        entries = listOf(
            NewEntry(dosage = "3", frequency = 1, note = "TEST - Prescription 1 - Entry 1", medicineId = 1),
            NewEntry(dosage = "2", frequency = 2, note = "TEST - Prescription 1 - Entry 2", medicineId = 2)
        )
    )

    val NEW_ENTRIES_1_JSON = "[" +
        "{" +
            "\"dosage\":\"${newPrescription.entries[0].dosage}\"," +
            "\"frequency\":${newPrescription.entries[0].frequency}," +
            "\"note\":\"${newPrescription.entries[0].note}\"," +
            "\"medicineId\":${newPrescription.entries[0].medicineId}" +
        "}," +
        "{" +
            "\"dosage\":\"${newPrescription.entries[1].dosage}\"," +
            "\"frequency\":${newPrescription.entries[1].frequency}," +
            "\"note\":\"${newPrescription.entries[1].note}\"," +
            "\"medicineId\":${newPrescription.entries[1].medicineId}" +
        "}" +
    "]"

    val NEW_PRESCRIPTION = "{" +
        "\"id\":${newPrescription.id}," +
        "\"appointmentId\":${newPrescription.appointmentId}," +
        "\"start\":\"${START_DATE_STR}\"," +
        "\"end\":\"${END_DATE_STR}\"," +
        "\"entries\":$NEW_ENTRIES_1_JSON" +
    "}"

}