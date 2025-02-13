package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val inputFields by viewModel.inputFields.collectAsState() // Используем строки, а не `Double`
    var showError by remember { mutableStateOf(false) }

    val isFormValid =
        inputFields["model"]?.isNotBlank() == true && inputFields["licensePlate"]?.isNotBlank() == true

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавление автомобиля") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = inputFields["model"] ?: "",
                onValueChange = { viewModel.updateField("model", it) },
                label = { Text("Модель автомобиля") },
                isError = showError && inputFields["model"].isNullOrBlank(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )

            OutlinedTextField(
                value = inputFields["licensePlate"] ?: "",
                onValueChange = { viewModel.updateField("licensePlate", it) },
                label = { Text("Гос. номер") },
                isError = showError && inputFields["licensePlate"].isNullOrBlank(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Норма заводки", style = MaterialTheme.typography.titleLarge)
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

            Text("Расход СУГ", style = MaterialTheme.typography.titleLarge)
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

            if (showError) {
                Text(
                    text = "Поля \"Модель\" и \"Гос. номер\" должны быть заполнены!",
                    color = MaterialTheme.colorScheme.error,
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
                if (newValue.matches(Regex("^[0-9]*[.,]?[0-9]*\$"))) {
                    onValueChange(newValue.replace(',', '.')) // Меняем запятую на точку
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

}