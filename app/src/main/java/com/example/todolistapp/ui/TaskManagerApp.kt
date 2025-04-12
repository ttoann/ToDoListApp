package com.example.todolistapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolistapp.model.SortOrder
import com.example.todolistapp.model.Task
import com.example.todolistapp.ui.components.DatePickerComponent
import com.example.todolistapp.ui.components.TaskItem
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerApp() {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var taskName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }
    var sortOrder by remember { mutableStateOf(SortOrder.BY_DATE) }
    var showAddTaskPanel by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Task Manager",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    // Sort Button with Label
                    var expanded by remember { mutableStateOf(false) }

                    Button(
                        onClick = { expanded = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Sort",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            when(sortOrder) {
                                SortOrder.BY_NAME -> "Name"
                                SortOrder.BY_DATE -> "Date"
                            }
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Sort by name") },
                            onClick = {
                                sortOrder = SortOrder.BY_NAME
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Sort by date") },
                            onClick = {
                                sortOrder = SortOrder.BY_DATE
                                expanded = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddTaskPanel = !showAddTaskPanel },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task"
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Add Task Panel - Only show when requested
            if (showAddTaskPanel) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = 4.dp,
                    shadowElevation = 2.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Create New Task",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider()

                        // Task Name Input
                        OutlinedTextField(
                            value = taskName,
                            onValueChange = { taskName = it },
                            label = { Text("Task Name") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors( // Fixed the colors API
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )

                        // DatePicker
                        DatePickerComponent(
                            selectedDate = selectedDate,
                            onDateSelected = { selectedDate = it }
                        )

                        // Add Button
                        Button(
                            onClick = {
                                if (taskName.isNotBlank()) {
                                    val newTask = Task(name = taskName, dueDate = selectedDate)
                                    tasks = tasks + newTask
                                    taskName = ""
                                    showAddTaskPanel = false
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Add Task", modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }

            // Task count and header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Tasks",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "${tasks.size} tasks",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Empty state
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tasks yet. Add your first task!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                // Tasks List with sorting
                val sortedTasks = when (sortOrder) {
                    SortOrder.BY_NAME -> tasks.sortedBy { it.name }
                    SortOrder.BY_DATE -> tasks.sortedBy { it.dueDate }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Padding for FAB
                ) {
                    items(sortedTasks) { task ->
                        TaskItem(
                            task = task,
                            dateFormatter = dateFormatter,
                            onDelete = { taskToDelete ->
                                tasks = tasks.filter { it.id != taskToDelete.id }
                            }
                        )
                    }
                }
            }
        }
    }
}