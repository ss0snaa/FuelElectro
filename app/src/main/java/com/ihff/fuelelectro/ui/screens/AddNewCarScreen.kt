package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ihff.fuelelectro.viewmodel.CarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewCarScreen(navController: NavController, viewModel: CarViewModel = hiltViewModel()) {
    val car by viewModel.car.collectAsState() // Теперь извлекаем всё состояние

    var showError by remember { mutableStateOf(false) }

    // Валидация: проверка на пустые поля
    val isFormValid = car.model.isNotBlank() && car.licensePlate.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавление автомобиля") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Основное инфо",
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.Start),
                style = MaterialTheme.typography.titleLarge,
            )

            // Модель автомобиля
            OutlinedTextField(
                value = car.model,
                onValueChange = { viewModel.updateField("model", it) },
                label = { Text("Модель автомобиля") },
                isError = showError && car.model.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )

            // Гос. номер
            OutlinedTextField(
                value = car.licensePlate,
                onValueChange = { viewModel.updateField("licensePlate", it) },
                label = { Text("Гос. номер") },
                isError = showError && car.licensePlate.isBlank(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Норма заводки
            Text("Норма заводки", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = if (car.normaZavodki08 == 0.0) "" else car.normaZavodki08.toString(),
                    onValueChange = { viewModel.updateField("normaZavodki08", it) },
                    label = { Text("0.8%") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = if (car.normaZavodki2 == 0.0) "" else car.normaZavodki2.toString(),
                    onValueChange = { viewModel.updateField("normaZavodki2", it) },
                    label = { Text("2%") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Расход СУГ
            Text("Расход СУГ", style = MaterialTheme.typography.titleLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = if (car.rashodSug1 == 0.0) "" else car.rashodSug1.toString(),
                    onValueChange = { viewModel.updateField("rashodSug1", it) },
                    label = { Text("По грунту") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = if (car.rashodSug2 == 0.0) "" else car.rashodSug2.toString(),
                    onValueChange = { viewModel.updateField("rashodSug2", it) },
                    label = { Text("Под линией") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = if (car.rashodSug3 == 0.0) "" else car.rashodSug3.toString(),
                    onValueChange = { viewModel.updateField("rashodSug3", it) },
                    label = { Text("По трассе") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Расход АИ-92
            Text("Расход АИ-92", style = MaterialTheme.typography.headlineSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = if (car.rashodBenz1 == 0.0) "" else car.rashodBenz1.toString(),
                    onValueChange = { viewModel.updateField("rashodBenz1", it) },
                    label = { Text("По грунту") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = if (car.rashodBenz2 == 0.0) "" else car.rashodBenz2.toString(),
                    onValueChange = { viewModel.updateField("rashodBenz2", it) },
                    label = { Text("Под линией") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
                OutlinedTextField(
                    value = if (car.rashodBenz3 == 0.0) "" else car.rashodBenz3.toString(),
                    onValueChange = { viewModel.updateField("rashodBenz3", it) },
                    label = { Text("По трассе") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )
            }

            // Текст ошибки
            if (showError) {
                Text(
                    text = "Поля \"Модель\" и \"Гос. номер\" должны быть заполнены!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isFormValid) {
                        viewModel.saveCar()
                        navController.popBackStack()
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid
            ) {
                Text("Сохранить".uppercase())
            }
        }
    }
}
