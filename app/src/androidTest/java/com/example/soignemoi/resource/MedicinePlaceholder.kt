package com.example.soignemoi.resource

import com.example.soignemoi.data.model.Medicine

object MedicinePlaceholder {

    val medicineResponsePlaceholder = listOf(
        Medicine(id = 1, title = "Paracetamol"),
        Medicine(id = 2, title = "Aspirine")
    )

    val MEDICINE_1_JSON = "{" +
        "\"id\":${medicineResponsePlaceholder[0].id}," +
        "\"title\":\"${medicineResponsePlaceholder[0].title}\"" +
    "}"

    val MEDICINE_2_JSON = "{" +
        "\"id\":${medicineResponsePlaceholder[1].id}," +
        "\"title\":\"${medicineResponsePlaceholder[1].title}\"" +
    "}"

    val MEDICINE_RESPONSE_JSON = "[" +
            "$MEDICINE_1_JSON, $MEDICINE_2_JSON" +
        "]"

}