package com.example.soignemoi.data.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.soignemoi.R

data class PrescriptionEntry(
    val prescriptionId: Int,
    val dosage: Int,
    val frequency: Int,
    val note: String,
    val medicine: Medicine
)


enum class Frequency(val id: Int, private val titleRes: Int) {
    DAILY(0, R.string.daily),
    WEEKLY(1, R.string.weekly),
    MONTHLY(2, R.string.monthly);

    val title: String
        @Composable
        get() = stringResource(id = titleRes)

    companion object {

        val all = listOf(DAILY, WEEKLY, MONTHLY)

        fun getFromId(id: Int): Frequency {
            return when (id) {
                0 -> DAILY
                1 -> WEEKLY
                2 -> MONTHLY
                else -> DAILY
            }
        }

    }
}
