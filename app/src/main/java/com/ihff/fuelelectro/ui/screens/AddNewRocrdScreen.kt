package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ihff.fuelelectro.viewmodel.AddNewRecordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewRecordScreen(
    navController: NavController,
    viewModel: AddNewRecordViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Добавление новой записи") },
                modifier = Modifier,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            PreviousWorkShiftData(viewModel = viewModel)
            Spacer(modifier = Modifier.size(16.dp))
            NowWorkShiftData()
            SaveCancelButtons(navController)
        }
    }
}

@Composable
fun NowWorkShiftData() {
    var nowWorkShiftDistance1 by remember { mutableStateOf("") }
    var nowWorkShiftDistance2 by remember { mutableStateOf("") }
    var nowWorkShiftDistance3 by remember { mutableStateOf("") }
    var nowWorkShiftOdometer by remember { mutableStateOf("") }


    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Данные текущей смены",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            label = { Text("Пройдено по грунту (1), км") },
            value = nowWorkShiftDistance1,
            onValueChange = { nowWorkShiftDistance1 = it },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Route, contentDescription = "Редактировать")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Пройдено по асфальту (3), км") },
            value = nowWorkShiftDistance3,
            onValueChange = { nowWorkShiftDistance3 = it },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Route, contentDescription = "Редактировать")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Пройдено под линией (2), км") },
            value = nowWorkShiftDistance2,
            onValueChange = { nowWorkShiftDistance2 = it },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Route, contentDescription = "Редактировать")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Показания одометра, км") },
            value = nowWorkShiftOdometer,
            onValueChange = { nowWorkShiftOdometer = it },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Route, contentDescription = "Редактировать")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CheckBoxVariants()

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun CheckBoxVariants() {
    var isCheckedAzs by remember { mutableStateOf(false) }
    var isChecked2Percent by remember { mutableStateOf(false) }
    var isCheckedCoefficient by remember { mutableStateOf(false) }

    var filledPetrol by remember { mutableStateOf("") }
    var filledLpg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckedAzs,
                onCheckedChange = { isCheckedAzs = it }
            )
            Text(
                "АЗС (был ли на заправке)",
            )
        }

        if (isCheckedAzs) {
            Column {
                OutlinedTextField(
                    label = { Text("Заправлено бензина, л") },
                    value = filledPetrol,
                    onValueChange = { filledPetrol = it },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.LocalGasStation, contentDescription = "Редактировать")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                OutlinedTextField(
                    label = { Text("Заправлено СУГ, л") },
                    value = filledLpg,
                    onValueChange = { filledLpg = it },
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.LocalGasStation, contentDescription = "Редактировать")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked2Percent,
                onCheckedChange = { isChecked2Percent = it }
            )
            Text(
                "2% (зимняя норма заводки)",
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isCheckedCoefficient,
                onCheckedChange = { isCheckedCoefficient = it }
            )
            Text(
                "Коэфициент 1,04 (зимний коэффициент расхода СУГ)",
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviousWorkShiftData(viewModel: AddNewRecordViewModel) {
    val prevWorkShiftOdometer by viewModel.prevWSOdometer.collectAsState()
    val prevWorkShiftPetrol by viewModel.prevWSPetrol.collectAsState()
    val prevWorkShiftLpg by viewModel.prevWSLpg.collectAsState()

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Данные прошлой смены",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            label = { Text("Показания одометра") },
            value = prevWorkShiftOdometer,
            onValueChange = { viewModel.updatePrevWSOdometer(it) },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Speed, contentDescription = "Редактировать")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Остаток бензина") },
            value = prevWorkShiftPetrol,
            onValueChange = { viewModel.updatePrevWSPetrol(it) },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.LocalGasStation, contentDescription = "Редактировать")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Остаток СУГ") },
            value = prevWorkShiftLpg,
            onValueChange = { viewModel.updatePrevWSLpg(it) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            leadingIcon = {
                Icon(Icons.Default.LocalGasStation, contentDescription = "Редактировать")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun SaveCancelButtons(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            enabled = false,
            onClick = {

            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text("Сохранить".uppercase())
        }
        Button(
            enabled = true,
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text("Отменить".uppercase())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddNewRecordScreenPreview() {
    AddNewRecordScreen(navController = rememberNavController())
}