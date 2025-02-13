package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ihff.fuelelectro.data.model.Car
import com.ihff.fuelelectro.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    val carsList by viewModel.carList.collectAsState()
    val selectedCar by viewModel.selectedCar.collectAsState()

    val selectCarDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Назад",
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
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Row {
                Icon(Icons.Default.DirectionsCar, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Автомобиль".uppercase(), style = MaterialTheme.typography.titleLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Выбранный автомобиль: \n${selectedCar?.model ?: "Не выбран"} \nГос. номер: ${selectedCar?.licensePlate ?: "-"}",
                    style = MaterialTheme.typography.bodyLarge
                )

                if (selectedCar != null) {
                    IconButton(onClick = {
                        selectedCar?.let { car ->
                            // Правильный переход для редактирования
                            navController.navigate("edit_car_screen/${car.id}")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Редактировать автомобиль"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { selectCarDialog.value = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Выбрать автомобиль")
            }
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
        }

        if (selectCarDialog.value) {
            SelectCarDialog(
                cars = carsList,
                onDismissRequest = { selectCarDialog.value = false },
                onCarSelected = { selectedCar ->
                    viewModel.selectCar(selectedCar)  // Выбор автомобиля
                    selectCarDialog.value = false
                },
                onAddCar = {
                    navController.navigate("add_new_car")  // Переход на экран добавления нового автомобиля
                },
                onEditCar = { car ->
                    // Переход на экран редактирования с передачей ID автомобиля
                    navController.navigate("edit_car_screen/${car.id}")
                },
                onDeleteCar = { car ->
                    viewModel.deleteCar(car)  // Удаляем автомобиль через ViewModel
                }
            )
        }
    }
}


@Composable
fun SelectCarDialog(
    cars: List<Car>,
    onDismissRequest: () -> Unit,
    onCarSelected: (Car) -> Unit,
    onAddCar: () -> Unit,
    onEditCar: (Car) -> Unit,
    onDeleteCar: (Car) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sortedCars = cars.sortedBy { it.model }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text("Выберите автомобиль") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp, max = screenHeight * 0.6f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(sortedCars) { car ->
                        var expanded by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    onCarSelected(car) // Выбираем автомобиль
                                }
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = car.model, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    text = "Гос. номер: ${car.licensePlate}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            // Иконка "три точки" для раскрытия меню
                            IconButton(onClick = { expanded = !expanded }) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "Меню"
                                )
                            }

                            // Меню с опциями редактирования и удаления
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Редактировать") },
                                    onClick = {
                                        onEditCar(car)
                                        expanded = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Удалить") },
                                    onClick = {
                                        onDeleteCar(car)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onAddCar() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Добавить")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Отмена")
            }
        }
    )
}
