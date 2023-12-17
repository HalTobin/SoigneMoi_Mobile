package com.example.soignemoi.feature.patient_details.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.soignemoi.R
import com.example.soignemoi.data.model.Prescription
import com.example.soignemoi.util.DateUtil.formattedDate
import com.example.soignemoi.util.DateUtil.howManyDays

@Composable
fun PrescriptionItem(
    modifier: Modifier = Modifier,
    prescription: Prescription,
    onSelect: (Prescription) -> Unit
) {

    @Composable
    fun Prescription.getDateString(): String {
        val days = howManyDays(this.start, this.end)
        return "${this.start.formattedDate} - ${this.end.formattedDate} : ${pluralStringResource(id = R.plurals.day, days, days)}"
    }

    Column {
        Column(modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelect(prescription) }
            .background(if (prescription.isStillUpToDate) MaterialTheme.colorScheme.surface else Color.LightGray)
            .padding(4.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = prescription.getMedicinesAsString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
            Text(modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
                text = prescription.getDateString(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.End)
        }
    }

}