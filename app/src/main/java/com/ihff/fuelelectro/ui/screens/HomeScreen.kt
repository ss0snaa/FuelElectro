package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ihff.fuelelectro.viewmodel.RecordViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: RecordViewModel = hiltViewModel()) {
    // Слушаем Flow allRecords с помощью collectAsState
    val records by viewModel.records.collectAsState(initial = emptyList())

    // Состояние для отслеживания прокрутки в LazyColumn
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Калькулятор топлива РЭС") },
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
            val fabVisible = scrollState.firstVisibleItemIndex == 0
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
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            // Заголовок
            if (records.isEmpty()) {
                Text(text = "Записей пока нет")
            }

            // LazyColumn для списка записей
            LazyColumn(
                state = scrollState,  // Используем scrollState для прокрутки
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(records.reversed()) { record ->
                    ListItem(
                        headlineContent = {
                            Text("Запись от: ${convertMillisToDate(record.dateRecord)}") },
                        supportingContent = { Text("Текущий километраж: ${record.nowWSOdometer}") },
                        leadingContent = {
                            Text("${record.id}")
                        },
                        trailingContent = {
                            IconButton(onClick = {
                                navController.navigate("shift_data_screen/${record.id}")
                            }) {
                                if (record.typeOfWork == "D") {
                                    Icon(Icons.Default.LightMode, contentDescription = "")
                                } else {
                                    Icon(Icons.Default.DarkMode, contentDescription = "")
                                }
                            }
                        },
                        modifier = Modifier
                            .clickable {
                                navController.navigate("shift_data_screen/${record.id}")
                            }
                    )
                    HorizontalDivider()
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
        Icon(Icons.Filled.Add, contentDescription = "Добавить запись")
        Text("Добавить запись")
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}