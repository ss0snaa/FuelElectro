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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ihff.fuelelectro.data.model.WorkType
import com.ihff.fuelelectro.viewmodel.RecordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewRecordScreen(
    navController: NavController,
    viewModel: RecordViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Добавление новой записи") },
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
            TypeOfWorksShift(viewModel = viewModel)
            Spacer(modifier = Modifier.size(16.dp))
            PreviousWorkShiftData(viewModel = viewModel)
            Spacer(modifier = Modifier.size(16.dp))
            NowWorkShiftData(viewModel = viewModel)
            SaveCancelButtons(navController, viewModel = viewModel)
        }
    }
}

@Composable
fun TypeOfWorksShift(viewModel: RecordViewModel) {
    val shiftData = viewModel.shiftData.collectAsState().value

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Тип смены",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Дневная смена
            Checkbox(
                checked = shiftData.typeOfWork == WorkType.DAY,
                onCheckedChange = {
                    viewModel.updateShiftData(shiftData.copy(typeOfWork = WorkType.DAY))
                }
            )
            Text("Дневная")

            // Ночная смена
            Checkbox(
                checked = shiftData.typeOfWork == WorkType.NIGHT,
                onCheckedChange = {
                    viewModel.updateShiftData(shiftData.copy(typeOfWork = WorkType.NIGHT))
                }
            )
            Text("Ночная")
        }
    }
}

@Composable
fun NowWorkShiftData(viewModel: RecordViewModel) {
    val shiftData = viewModel.shiftData.collectAsState().value

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Данные текущей смены",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            label = { Text("Пройдено по грунту (1), км") },
            value = shiftData.nowDistance1.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(nowDistance1 = it)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Route, contentDescription = "Редактировать") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Пройдено по асфальту (3), км") },
            value = shiftData.nowDistance3.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(nowDistance3 = it)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Route, contentDescription = "Редактировать") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Пройдено под линией (2), км") },
            value = shiftData.nowDistance2.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(nowDistance2 = it)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Route, contentDescription = "Редактировать") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        OutlinedTextField(
            label = { Text("Показания одометра, км") },
            value = shiftData.nowOdometer.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(nowOdometer = it)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Route, contentDescription = "Редактировать") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        CheckBoxVariants(viewModel = viewModel)

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun CheckBoxVariants(viewModel: RecordViewModel) {
    val shiftData = viewModel.shiftData.collectAsState().value

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        // Чекбокс АЗС
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = shiftData.isAzs,
                onCheckedChange = { viewModel.updateShiftData(shiftData.copy(isAzs = it)) }
            )
            Text("АЗС (был ли на заправке)")
        }

        if (shiftData.isAzs) {
            OutlinedTextField(
                label = { Text("Заправлено бензина, л") },
                value = shiftData.azsPetrol.toString(),
                onValueChange = {
                    viewModel.updateShiftData(
                        shiftData.copy(
                            azsPetrol = it
                        )
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.LocalGasStation,
                        contentDescription = "Редактировать"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                label = { Text("Заправлено СУГ, л") },
                value = shiftData.azsLpg.toString(),
                onValueChange = {
                    viewModel.updateShiftData(
                        shiftData.copy(
                            azsLpg = it
                        )
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.LocalGasStation,
                        contentDescription = "Редактировать"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
        }

        // Чекбокс 2% и 4% для зимней нормы заводки
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = shiftData.is2Percent,
                onCheckedChange = {
                    viewModel.updateShiftData(shiftData.copy(is2Percent = it, is4Percent = !it))
                }
            )
            Text("2% (зимняя норма заводки)")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = shiftData.is4Percent,
                onCheckedChange = {
                    viewModel.updateShiftData(shiftData.copy(is4Percent = it, is2Percent = !it))
                }
            )
            Text("4% (зимняя норма заводки)")
        }

        // Чекбокс для коэффициентов
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = shiftData.isCoefficient104,
                onCheckedChange = {
                    viewModel.updateShiftData(
                        shiftData.copy(
                            isCoefficient104 = it,
                            isCoefficient108 = !it
                        )
                    )
                }
            )
            Text("Коэффициент 1,04 (зимний коэффициент расхода СУГ)")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = shiftData.isCoefficient108,
                onCheckedChange = {
                    viewModel.updateShiftData(
                        shiftData.copy(
                            isCoefficient108 = it,
                            isCoefficient104 = !it
                        )
                    )
                }
            )
            Text("Коэффициент 1,08 (зимний коэффициент расхода СУГ)")
        }
    }
}

@Composable
fun PreviousWorkShiftData(viewModel: RecordViewModel) {
    val shiftData = viewModel.shiftData.collectAsState().value

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Данные прошлой смены",
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        // Поле для одометра
        OutlinedTextField(
            label = { Text("Показания одометра") },
            value = shiftData.prevOdometer.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(prevOdometer = it)) },
            singleLine = true,
            leadingIcon = { Icon(Icons.Default.Speed, contentDescription = "Редактировать") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        // Поле для остатка бензина
        OutlinedTextField(
            label = { Text("Остаток бензина") },
            value = shiftData.prevPetrol.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(prevPetrol = it)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.LocalGasStation,
                    contentDescription = "Редактировать"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        // Поле для остатка СУГ
        OutlinedTextField(
            label = { Text("Остаток СУГ") },
            value = shiftData.prevLpg.toString(),
            onValueChange = { viewModel.updateShiftData(shiftData.copy(prevLpg = it)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    Icons.Default.LocalGasStation,
                    contentDescription = "Редактировать"
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun SaveCancelButtons(navController: NavController, viewModel: RecordViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            enabled = true, // TODO: Проверить, можно ли сохранить
            onClick = {
                viewModel.saveRecord()
                navController.popBackStack()
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
