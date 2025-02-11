package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ihff.fuelelectro.viewmodel.CarViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCarScreen(
    navController: NavController,
    carId: Int?,  // Получаем carId из навигации
    viewModel: CarViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val inputFields by viewModel.inputFields.collectAsState() // Получаем текстовые данные

    // Загружаем данные автомобиля при входе в экран
    LaunchedEffect(carId) {
        if (carId != null) {
            viewModel.getCarById(carId)  // Загружаем данные, если carId не null
        } else {
            viewModel.clearCar()  // Очистка данных при добавлении нового автомобиля
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование автомобиля") },
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
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Поля для ввода модели и номера автомобиля
            OutlinedTextField(
                value = inputFields["model"] ?: "",
                onValueChange = { viewModel.updateField("model", it) },
                label = { Text("Модель автомобиля") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = inputFields["licensePlate"] ?: "",
                onValueChange = { viewModel.updateField("licensePlate", it) },
                label = { Text("Гос. номер") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Поля для ввода нормы заводки
            Text("Норма заводки", style = MaterialTheme.typography.headlineSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomNumberField(
                    value = inputFields["normaZavodki08"] ?: "",
                    onValueChange = { viewModel.updateField("normaZavodki08", it) },
                    label = "0.8%",
                    modifier = Modifier.weight(1f)
                )

                CustomNumberField(
                    value = inputFields["normaZavodki2"] ?: "",
                    onValueChange = { viewModel.updateField("normaZavodki2", it) },
                    label = "2%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Поля для расхода СУГ
            Text("Расход СУГ", style = MaterialTheme.typography.headlineSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomNumberField(
                    value = inputFields["rashodSug1"] ?: "",
                    onValueChange = { viewModel.updateField("rashodSug1", it) },
                    label = "По грунту",
                    modifier = Modifier.weight(1f)
                )

                CustomNumberField(
                    value = inputFields["rashodSug2"] ?: "",
                    onValueChange = { viewModel.updateField("rashodSug2", it) },
                    label = "Под линией",
                    modifier = Modifier.weight(1f)
                )

                CustomNumberField(
                    value = inputFields["rashodSug3"] ?: "",
                    onValueChange = { viewModel.updateField("rashodSug3", it) },
                    label = "По трассе",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Поля для расхода АИ-92
            Text("Расход АИ-92", style = MaterialTheme.typography.headlineSmall)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CustomNumberField(
                    value = inputFields["rashodBenz1"] ?: "",
                    onValueChange = { viewModel.updateField("rashodBenz1", it) },
                    label = "По грунту",
                    modifier = Modifier.weight(1f)
                )

                CustomNumberField(
                    value = inputFields["rashodBenz2"] ?: "",
                    onValueChange = { viewModel.updateField("rashodBenz2", it) },
                    label = "Под линией",
                    modifier = Modifier.weight(1f)
                )

                CustomNumberField(
                    value = inputFields["rashodBenz3"] ?: "",
                    onValueChange = { viewModel.updateField("rashodBenz3", it) },
                    label = "По трассе",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка "Сохранить изменения"
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveCar()
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Сохранить изменения")
            }
        }
    }
}


@Composable
fun CustomNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Разрешаем только цифры, точку и запятую
            if (newValue.matches(Regex("^[0-9]*[.,]?[0-9]*\$"))) {
                onValueChange(newValue.replace(',', '.')) // Авто-замена запятой на точку
            }
        },
        label = { Text(label) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = ImeAction.Done
        )
    )
}
