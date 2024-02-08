package com.example.soignemoi.feature.prescription.presentation.component

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    minimumInMillis: Long = getTimestampForMidnight(),
    maximumInMillis: Long = Long.MAX_VALUE,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = minimumInMillis + 86400000,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return (utcTimeMillis >= minimumInMillis) && (utcTimeMillis <= maximumInMillis)
            }
    })

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(Date(datePickerState.selectedDateMillis ?: System.currentTimeMillis()))
                onDismiss()
            }) { Text(text = "OK") }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) { Text(text = "Cancel") }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@SuppressLint("SimpleDateFormat")
private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

fun getTimestampForMidnight(): Long {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return calendar.timeInMillis
}