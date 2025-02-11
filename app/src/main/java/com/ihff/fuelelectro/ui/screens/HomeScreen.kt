package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ihff.fuelelectro.viewmodel.RecordViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: RecordViewModel = hiltViewModel()) {
    // Слушаем Flow allRecords с помощью collectAsState
    val records by viewModel.allRecords.collectAsState(initial = emptyList())

    // Состояние для отслеживания прокрутки
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Калькулятор топлива РЭС") },
                modifier = Modifier,
                actions = {
                    IconButton(onClick = {
                        navController.navigate("settings_screen")
                    }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Настройки",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
        floatingActionButton = {
            // Скрытие FAB при прокрутке
            val fabVisible = scrollState.value == 0
            if (fabVisible) {
                AddNewRecordFab(onClick = {
                    navController.navigate("add_new_record_screen")
                })
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState), // Прокручиваемая область
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (records.isEmpty()) {
                Text(text = "Записей пока нет")
            } else {
                // TODO: Отображаем список записей
                records.reversed().forEach {
                    Text(text = "Запись: $it")
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun AddNewRecordFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = { onClick() },
    ) {
        Icon(Icons.Filled.Add, "Добавить запись")
        Text("Добавить запись")
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}