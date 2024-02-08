package com.example.soignemoi.feature.prescription

import com.example.soignemoi.data.model.Frequency
import com.example.soignemoi.data.model.Medicine
import com.example.soignemoi.feature.prescription.data.NewEntry
import com.example.soignemoi.feature.prescription.data.asString
import com.example.soignemoi.feature.prescription.presentation.PrescriptionState
import com.example.soignemoi.resource.ConstPlaceholder.END_DATE
import com.example.soignemoi.resource.ConstPlaceholder.START_DATE
import org.junit.Assert
import org.junit.Test
import java.util.Calendar

class PrescriptionTest {

    @Test
    fun `test NewEntry isFilled method with all field completed`() {
        // Given
        val entry = NewEntry(dosage = "1", frequency = Frequency.DAILY.id, note = "Test", medicineId = 1)

        // When
        val actual = entry.isFilled

        // Assert
        Assert.assertEquals(true, actual)
    }

    @Test
    fun `test NewEntry isFilled method with uncompleted field`() {
        // Given
        val entry = NewEntry(dosage = "1", frequency = Frequency.DAILY.id, note = "Test", medicineId = null)

        // When
        val actual = entry.isFilled

        // Assert
        Assert.assertEquals(false, actual)
    }

    @Test
    fun `test NewEntry isFilled method with wrong fields value`() {
        // Given
        val entry = NewEntry(dosage = "0", frequency = Frequency.DAILY.id, note = "Test", medicineId = 1)

        // When
        val actual = entry.isFilled

        // Assert
        Assert.assertEquals(false, actual)
    }

    @Test
    fun `test NewEntry medicineTitle method for correct values`() {
        // Given
        val medicines = listOf(
            Medicine(0, "Test 1"),
            Medicine(1, "Test 2"),
            Medicine(2, "Test 3")
        )
        val newEntry = NewEntry(medicineId = 1)

        // When
        val actual = newEntry.medicineTitle(medicines)

        // Assert
        Assert.assertEquals(medicines[1].title, actual)
    }

    @Test
    fun `test NewEntry medicineTitle method for wrong values`() {
        // Given
        val medicines = listOf(
            Medicine(0, "Test 1"),
            Medicine(1, "Test 2"),
            Medicine(2, "Test 3")
        )
        val newEntry = NewEntry(medicineId = 10)

        // When
        val actual = newEntry.medicineTitle(medicines)

        // Assert
        Assert.assertEquals("", actual)
    }

    @Test
    fun `new NewPrescription Date to String method`() {
        // Given
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 27)

        // When
        val actual = calendar.time.asString

        // Assert
        Assert.assertEquals("2023-12-27", actual)
    }

    @Test
    fun `test PrescriptionState canSave method, with completed fields`() {
        // Given
        val state = PrescriptionState(
            appointmentId = 1,
            dateStart = START_DATE,
            dateEnd = END_DATE,
            entries = listOf(NewEntry())
        )

        // When
        val actual = state.canSave()

        // Assert
        Assert.assertEquals(true, actual)
    }

    @Test
    fun `test PrescriptionState canSave method, with uncompleted fields`() {
        // Given
        val state = PrescriptionState(
            appointmentId = null,
            dateStart = START_DATE,
            dateEnd = END_DATE,
            entries = emptyList()
        )

        // When
        val actual = state.canSave()

        // Assert
        Assert.assertEquals(false, actual)
    }

    @Test
    fun `test PrescriptionState startFormatted method`() {
        // Given
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 27)
        val state = PrescriptionState(
            dateStart = calendar.time,
        )

        // When
        val actual = state.startFormatted

        // Assert
        Assert.assertEquals("27 décembre 2023", actual)
    }

    @Test
    fun `test PrescriptionState startFormatted method with null value`() {
        // Given
        val state = PrescriptionState(
            dateStart = null,
        )

        // When
        val actual = state.startFormatted

        // Assert
        Assert.assertEquals("", actual)
    }

    @Test
    fun `test PrescriptionState endFormatted method`() {
        // Given
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, 11)
        calendar.set(Calendar.DAY_OF_MONTH, 27)
        val state = PrescriptionState(
            dateEnd = calendar.time,
        )

        // When
        val actual = state.endFormatted

        // Assert
        Assert.assertEquals("27 décembre 2023", actual)
    }

    @Test
    fun `test PrescriptionState endFormatted method with null value`() {
        // Given
        val state = PrescriptionState(
            dateEnd = null,
        )

        // When
        val actual = state.endFormatted

        // Assert
        Assert.assertEquals("", actual)
    }

}