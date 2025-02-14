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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    val selectedTypeOfWork = viewModel.typeOfWorksShift.collectAsState()

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Тип смены",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Дневная смена
            Checkbox(
                checked = selectedTypeOfWork.value == "D",
                onCheckedChange = {
                    if (selectedTypeOfWork.value != "D") {
                        viewModel.updateTypeOfWorkShift("D")
                    } else {
                        viewModel.updateTypeOfWorkShift("")
                    }
                }
            )
            Text("Дневная")

            // Ночная смена
            Checkbox(
                checked = selectedTypeOfWork.value == "N",
                onCheckedChange = {
                    if (selectedTypeOfWork.value != "N") {
                        viewModel.updateTypeOfWorkShift("N")
                    } else {
                        viewModel.updateTypeOfWorkShift("")
                    }
                }
            )
            Text("Ночная")
        }
    }
}


@Composable
fun NowWorkShiftData(viewModel: RecordViewModel) {
    val nowWSD1 by viewModel.nowWSD1.collectAsState()
    val nowWSD2 by viewModel.nowWSD2.collectAsState()
    val nowWSD3 by viewModel.nowWSD3.collectAsState()
    val nowWSOdometer by viewModel.nowWSDOdometer.collectAsState()

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
            value = nowWSD1,
            onValueChange = { viewModel.updateNowWSD1(it) },
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
            value = nowWSD3,
            onValueChange = { viewModel.updateNowWSD3(it) },
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
            value = nowWSD2,
            onValueChange = { viewModel.updateNowWSD2(it) },
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
            value = nowWSOdometer,
            onValueChange = { viewModel.updateNowWSDOdometer(it) },
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Default.Route, contentDescription = "Редактировать")
            },
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
    val cbxAzs by viewModel.cbxAzs.collectAsState()
    val azsPetrol by viewModel.azsPetrol.collectAsState()
    val azsLpg by viewModel.azsLpg.collectAsState()
    val cbx2Percent by viewModel.cbx2Percent.collectAsState()
    val cbx4Percent by viewModel.cbx4Percent.collectAsState()
    val cbxCoefficient104 by viewModel.cbxCoefficient104.collectAsState()
    val cbxCoefficient108 by viewModel.cbxCoefficient108.collectAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
// Чекбокс АЗС
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = cbxAzs,
                onCheckedChange = { viewModel.updateCbxAzs(it) }
            )
            Text("АЗС (был ли на заправке)")
        }

        if (cbxAzs) {
            Column {
                OutlinedTextField(
                    label = { Text("Заправлено бензина, л") },
                    value =  azsPetrol,
                    onValueChange = { viewModel.updateAzsPetrol(it)},
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
                    value =  azsLpg,
                    onValueChange = { viewModel.updateAzsLpg(it) },
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
        }

// Чекбокс "2% (зимняя норма заводки)"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = cbx2Percent,
                onCheckedChange = { viewModel.updateCbx2Percent(it) }
            )
            Text("2% (зимняя норма заводки)")
        }

        // Чекбокс "4% (зимняя норма заводки)"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = cbx4Percent,
                onCheckedChange = { viewModel.updateCbx4Percent(it) }
            )
            Text("4% (зимняя норма заводки)")
        }

// Чекбокс "Коэффициент 1,04 (зимний коэффициент расхода СУГ)"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = cbxCoefficient104,
                onCheckedChange = {
                    viewModel.updateCbxCoefficient104(it)
                }
            )
            Text("Коэффициент 1,04 (зимний коэффициент расхода СУГ)")
        }

// Чекбокс "Коэффициент 1,08 (зимний коэффициент расхода СУГ)"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = cbxCoefficient108,
                onCheckedChange = {
                    viewModel.updateCbxCoefficient108(it)
                }
            )
            Text("Коэффициент 1,08 (зимний коэффициент расхода СУГ)")
        }
    }
}


@Composable
fun PreviousWorkShiftData(viewModel: RecordViewModel) {
    val prevWSOdometer by viewModel.prevWSOdometer.collectAsState()
    val prevWSPetrol by viewModel.prevWSPetrol.collectAsState()
    val prevWSLpg by viewModel.prevWSLpg.collectAsState()

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
            value = prevWSOdometer,
            onValueChange = { viewModel.updatePrevWSOdometer(it) },
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
            value = prevWSPetrol,
            onValueChange = { viewModel.updatePrevWSPetrol(it) },
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
            value = prevWSLpg,
            onValueChange = { viewModel.updatePrevWSLpg(it) },
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
                navController.popBackStack() // TODO: Добавить переход на экран смены
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
