package com.example.soignemoi.feature.prescription.presentation.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.soignemoi.data.model.Medicine

@Composable
fun SelectMedicineDialog(
    medicines: List<Medicine>,
    onSelect: (Medicine) -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp)
        ) {

        }
    }

}