package com.example.soignemoi.resource

import com.example.soignemoi.data.model.Appointment
import com.example.soignemoi.data.model.Doctor
import com.example.soignemoi.data.model.Note
import com.example.soignemoi.data.model.Patient
import com.example.soignemoi.data.model.PatientData
import com.example.soignemoi.data.model.Prescription
import com.example.soignemoi.data.model.PrescriptionEntry
import com.example.soignemoi.data.model.Specialty
import com.example.soignemoi.resource.ConstPlaceholder.END_DATE
import com.example.soignemoi.resource.ConstPlaceholder.END_DATE_STR
import com.example.soignemoi.resource.ConstPlaceholder.START_DATE
import com.example.soignemoi.resource.ConstPlaceholder.START_DATE_STR
import com.example.soignemoi.resource.ConstPlaceholder.VISITOR_ID

object PatientPlaceholder {

    val specialtyPlaceholder = Specialty(1, "Rhumatologie")

    val doctorPlaceholder = Doctor(
        id = 1,
        name = "John",
        surname = "Doe",
        registrationNumber = "REG123",
        specialty = specialtyPlaceholder
    )

    val appointmentPlaceholder = Appointment(
        id = 1,
        visitorId = VISITOR_ID,
        startDate = START_DATE,
        endDate = END_DATE,
        reason = "TEST",
        specialty = specialtyPlaceholder.title,
        doctor = doctorPlaceholder.registrationNumber
    )

    val notesPlaceholder = listOf(
        Note(
            id = 1,
            appointmentId = appointmentPlaceholder.id,
            doctorId = doctorPlaceholder.id,
            userId = VISITOR_ID,
            title = "Note 1",
            content = "Content 1",
            date = START_DATE
        ),
        Note(
            id = 2,
            appointmentId = appointmentPlaceholder.id,
            doctorId = doctorPlaceholder.id,
            userId = VISITOR_ID,
            title = "Note 2",
            content = "Content 2",
            date = START_DATE
        )
    )

    val prescriptionsPlaceholder = listOf(
        Prescription(
            id = 1,
            appointmentId = appointmentPlaceholder.id,
            start = START_DATE,
            end = END_DATE,
            entries = listOf(
                PrescriptionEntry(prescriptionId = 1, dosage = 3, frequency = 1, note = "TEST - Prescription 1 - Entry 1", medicine = MedicinePlaceholder.medicineResponsePlaceholder[0]),
                PrescriptionEntry(prescriptionId = 1, dosage = 2, frequency = 2, note = "TEST - Prescription 1 - Entry 2", medicine = MedicinePlaceholder.medicineResponsePlaceholder[1])
            )
        ),
        Prescription(
            id = 2,
            appointmentId = appointmentPlaceholder.id,
            start = START_DATE,
            end = END_DATE,
            entries = listOf(
                PrescriptionEntry(prescriptionId = 2, dosage = 1, frequency = 2, note = "TEST - Prescription 2 - Entry 1", medicine = MedicinePlaceholder.medicineResponsePlaceholder[0]),
                PrescriptionEntry(prescriptionId = 2, dosage = 5, frequency = 0, note = "TEST - Prescription 2 - Entry 2", medicine = MedicinePlaceholder.medicineResponsePlaceholder[1])
            )
        )
    )

    val patientDataResponsePlaceholder = PatientData(
        id = VISITOR_ID,
        name = "TEST",
        surname = "TEST",
        appointment = appointmentPlaceholder,
        doctor = doctorPlaceholder,
        specialty = specialtyPlaceholder,
        startDate = START_DATE,
        endDate = END_DATE,
        notes = notesPlaceholder,
        prescriptions = prescriptionsPlaceholder
    )

    val patientsResponsePlaceholder = listOf(
        Patient(
            id = 1,
            name = "TEST_1_NAME",
            surname = "TEST_1_SURNAME",
            reason = "TEST_1_REASON"
        ),
        Patient(
            id = 2,
            name = "TEST_2_NAME",
            surname = "TEST_2_SURNAME",
            reason = "TEST_2_REASON"
        )
    )

    val PATIENTS_RESPONSE_JSON = "[" +
        "{" +
            "\"id\": ${patientsResponsePlaceholder[0].id}," +
            "\"name\": \"${patientsResponsePlaceholder[0].name}\"," +
            "\"surname\": \"${patientsResponsePlaceholder[0].surname}\"," +
            "\"reason\": \"${patientsResponsePlaceholder[0].reason}\"" +
        "}," +
        "{" +
            "\"id\": ${patientsResponsePlaceholder[1].id}," +
            "\"name\": \"${patientsResponsePlaceholder[1].name}\"," +
            "\"surname\": \"${patientsResponsePlaceholder[1].surname}\"," +
            "\"reason\": \"${patientsResponsePlaceholder[1].reason}\"" +
        "}" +
    "]"

    val APPOINTMENT_JSON = "{" +
        "\"id\":${appointmentPlaceholder.id}," +
        "\"visitorId\":${appointmentPlaceholder.visitorId}," +
        "\"startDate\":\"${START_DATE_STR}\"," +
        "\"endDate\":\"${END_DATE_STR}\"," +
        "\"reason\":\"${appointmentPlaceholder.reason}\"," +
        "\"specialty\":\"${appointmentPlaceholder.specialty}\"," +
        "\"doctor\":\"${appointmentPlaceholder.doctor}\"" +
    "}"

    val SPECIALTY_JSON = "{" +
        "\"id\":${specialtyPlaceholder.id}," +
        "\"title\":\"${specialtyPlaceholder.title}\"" +
    "}"

    val DOCTOR_JSON = "{" +
        "\"id\":${doctorPlaceholder.id}," +
        "\"name\":\"${doctorPlaceholder.name}\"," +
        "\"surname\":\"${doctorPlaceholder.surname}\"," +
        "\"registrationNumber\":\"${doctorPlaceholder.registrationNumber}\"," +
        "\"specialty\":$SPECIALTY_JSON" +
    "}"

    val NOTES_JSON = "[" +
        "{" +
            "\"id\":${notesPlaceholder[0].id}," +
            "\"appointmentId\":${notesPlaceholder[0].appointmentId}," +
            "\"doctorId\":${notesPlaceholder[0].doctorId}," +
            "\"userId\":${notesPlaceholder[0].userId}," +
            "\"title\":\"${notesPlaceholder[0].title}\"," +
            "\"content\":\"${notesPlaceholder[0].content}\"," +
            "\"date\":\"${START_DATE_STR}\"" +
        "}," +
        "{" +
            "\"id\":${notesPlaceholder[1].id}," +
            "\"appointmentId\":${notesPlaceholder[1].appointmentId}," +
            "\"doctorId\":${notesPlaceholder[1].doctorId}," +
            "\"userId\":${notesPlaceholder[1].userId}," +
            "\"title\":\"${notesPlaceholder[1].title}\"," +
            "\"content\":\"${notesPlaceholder[1].content}\"," +
            "\"date\":\"${START_DATE_STR}\"" +
        "}" +
    "]"

    val ENTRIES_1_JSON = "[" +
        "{" +
            "\"prescriptionId\":${prescriptionsPlaceholder[0].entries[0].prescriptionId}," +
            "\"dosage\":${prescriptionsPlaceholder[0].entries[0].dosage}," +
            "\"frequency\":${prescriptionsPlaceholder[0].entries[0].frequency}," +
            "\"note\":\"${prescriptionsPlaceholder[0].entries[0].note}\"," +
            "\"medicine\":${MedicinePlaceholder.MEDICINE_1_JSON}" +
        "}," +
        "{" +
            "\"prescriptionId\":${prescriptionsPlaceholder[0].entries[1].prescriptionId}," +
            "\"dosage\":${prescriptionsPlaceholder[0].entries[1].dosage}," +
            "\"frequency\":${prescriptionsPlaceholder[0].entries[1].frequency}," +
            "\"note\":\"${prescriptionsPlaceholder[0].entries[1].note}\"," +
            "\"medicine\":${MedicinePlaceholder.MEDICINE_2_JSON}" +
        "}" +
    "]"

    val ENTRIES_2_JSON = "[" +
        "{" +
            "\"prescriptionId\":${prescriptionsPlaceholder[1].entries[0].prescriptionId}," +
            "\"dosage\":${prescriptionsPlaceholder[1].entries[0].dosage}," +
            "\"frequency\":${prescriptionsPlaceholder[1].entries[0].frequency}," +
            "\"note\":\"${prescriptionsPlaceholder[1].entries[0].note}\"," +
            "\"medicine\":${MedicinePlaceholder.MEDICINE_1_JSON}" +
        "}," +
        "{" +
            "\"prescriptionId\":${prescriptionsPlaceholder[1].entries[1].prescriptionId}," +
            "\"dosage\":${prescriptionsPlaceholder[1].entries[1].dosage}," +
            "\"frequency\":${prescriptionsPlaceholder[1].entries[1].frequency}," +
            "\"note\":\"${prescriptionsPlaceholder[1].entries[1].note}\"," +
            "\"medicine\":${MedicinePlaceholder.MEDICINE_2_JSON}" +
        "}" +
    "]"

    val PRESCRIPTIONS_JSON = "[" +
        "{" +
            "\"id\":${prescriptionsPlaceholder[0].id}," +
            "\"appointmentId\":${prescriptionsPlaceholder[0].appointmentId}," +
            "\"start\":\"${START_DATE_STR}\"," +
            "\"end\":\"${END_DATE_STR}\"," +
            "\"entries\":$ENTRIES_1_JSON" +
        "}," +
        "{" +
            "\"id\":${prescriptionsPlaceholder[1].id}," +
            "\"appointmentId\":${prescriptionsPlaceholder[1].appointmentId}," +
            "\"start\":\"${START_DATE_STR}\"," +
            "\"end\":\"${END_DATE_STR}\"," +
            "\"entries\":$ENTRIES_2_JSON" +
        "}" +
    "]"

    val PATIENT_DATA_RESPONSE_JSON = "{" +
        "\"id\":${patientDataResponsePlaceholder.id}," +
        "\"name\":\"${patientDataResponsePlaceholder.name}\"," +
        "\"surname\":\"${patientDataResponsePlaceholder.surname}\"," +
        "\"appointment\":${APPOINTMENT_JSON}," +
        "\"doctor\":${DOCTOR_JSON}," +
        "\"specialty\":${SPECIALTY_JSON}," +
        "\"startDate\":\"${START_DATE_STR}\"," +
        "\"endDate\":\"${END_DATE_STR}\"," +
        "\"notes\":$NOTES_JSON," +
        "\"prescriptions\":$PRESCRIPTIONS_JSON" +
    "}"

}