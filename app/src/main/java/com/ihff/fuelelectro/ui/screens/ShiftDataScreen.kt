package com.ihff.fuelelectro.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ihff.fuelelectro.viewmodel.ShiftDataViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShiftDataScreen(
    navController: NavController,
    viewModel: ShiftDataViewModel = hiltViewModel(),
    recordId: Long?
) {
    // Загружаем запись по ID, если recordId не null
    LaunchedEffect(recordId) {
        recordId?.let { viewModel.loadRecordById(it) }
    }

    // Наблюдаем за StateFlow с данными текущей записи
    val record by viewModel.record.collectAsState()

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text("Итог смены") },
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
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxWidth(),
        ) {
            Text("Итог по смене", style = MaterialTheme.typography.titleLarge)

            // Если запись загружена, отображаем её данные, иначе выводим сообщение
            record?.let {
                Text("ID: ${it.id}")
                Text("Дата записи: ${convertMillisToDate(it.dateRecord)}")
                Text("Тип смены: ${it.typeOfWork}")

                Text("Пройдено по грунту: ${it.nowWSD1} км")
                Text("Пройдено под линией: ${it.nowWSD2} км")
                Text("Пройдено по трассе: ${it.nowWSD3} км")
                Text("Показания одометра (текущие): ${it.nowWSOdometer} км")

                Text("АЗС: ${if (it.cbxAzs) "Да" else "Нет"}")
                Text("Заправлено бензина: ${it.azsPetrol} л")
                Text("Заправлено СУГ: ${it.azsLpg} л")

                Text("Использована 2% норма: ${if (it.cbx2Percent) "Да" else "Нет"}")
                Text("Коэффициент 1.04: ${if (it.cbxCoefficient104) "Да" else "Нет"}")
                Text("Коэффициент 1.08: ${if (it.cbxCoefficient108) "Да" else "Нет"}")

                Text("Показания одометра (прошлая смена): ${it.prevWSOdometer} км")
                Text("Остаток бензина с прошлой смены: ${it.prevWSPetrol} л")
                Text("Остаток СУГ с прошлой смены: ${it.prevWSLpg} л")
            } ?: Text("Запись не найдена")
        }
    }


    // Функция для конвертации миллисекунд в дату
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(millis))
    }

}