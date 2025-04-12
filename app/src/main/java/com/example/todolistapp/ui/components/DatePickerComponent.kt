package com.example.todolistapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    selectedDate: Date,
    onDateSelected: (Date) -> Unit
) {
    val today = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())

    // Convert Date to calendar for DatePicker
    val calendar = Calendar.getInstance().apply {
        time = selectedDate
    }

    // Card-based date selector
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "Calendar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Due Date",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dateFormatter.format(selectedDate),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = calendar.timeInMillis,
            yearRange = 2023..2030,
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= today
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val newDate = Date(millis)
                            onDateSelected(newDate)
                        }
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Set Date")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDatePicker = false }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Select Due Date",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(10.dp)
                )
                Divider(thickness = 1.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.fillMaxWidth(),
                        showModeToggle = true,
                        title = null,
                        colors = DatePickerDefaults.colors(
                            // Ensure consistent styling for day cells
                            weekdayContentColor = MaterialTheme.colorScheme.onSurface,
                            dayContentColor = MaterialTheme.colorScheme.onSurface,
                            selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                            selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                            todayContentColor = MaterialTheme.colorScheme.primary,
                            todayDateBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
        }
    }
}