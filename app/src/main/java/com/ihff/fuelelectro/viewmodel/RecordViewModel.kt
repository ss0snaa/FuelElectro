package com.ihff.fuelelectro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihff.fuelelectro.data.model.Record
import com.ihff.fuelelectro.data.repository.RecordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val repository: RecordRepository
) : ViewModel() {

    // Основное состояние записи
    private val _shiftData = MutableStateFlow(Record())
    val shiftData: StateFlow<Record> = _shiftData.asStateFlow()

    // Временное состояние для хранения текстовых данных полей
    private val _inputFields = MutableStateFlow<Map<String, String>>(emptyMap())
    val inputFields: StateFlow<Map<String, String>> = _inputFields.asStateFlow()

    // Обновление текстового значения поля
    fun updateField(fieldName: String, value: String) {
        _inputFields.value = _inputFields.value.toMutableMap().also { it[fieldName] = value }

        // Обновляем основное состояние
        _shiftData.value = _shiftData.value.copy(
            azsPetrol = parseDouble(_inputFields.value["azsPetrol"]),
            azsLpg = parseDouble(_inputFields.value["azsLpg"]),

            cbxAzs = _shiftData.value.cbxAzs,  // Чекбоксы обновляются отдельно
            cbx2Percent = _shiftData.value.cbx2Percent,
            cbxCoefficient = _shiftData.value.cbxCoefficient,

            typeOfWork = _inputFields.value["typeOfWork"] ?: "",

            nowWSD1 = parseNumber(_inputFields.value["nowWSD1"]),
            nowWSD2 = parseNumber(_inputFields.value["nowWSD2"]),
            nowWSD3 = parseNumber(_inputFields.value["nowWSD3"]),
            nowWSOdometer = parseNumber(_inputFields.value["nowWSOdometer"]),

            prevWSOdometer = parseNumber(_inputFields.value["prevWSOdometer"]),
            prevWSPetrol = parseDouble(_inputFields.value["prevWSPetrol"]),
            prevWSLpg = parseDouble(_inputFields.value["prevWSLpg"])


        )
    }

    // Обновление чекбоксов
    fun updateCheckbox(fieldName: String, isChecked: Boolean) {
        _shiftData.value = when (fieldName) {
            "cbxAzs" -> _shiftData.value.copy(cbxAzs = isChecked)
            "cbx2Percent" -> _shiftData.value.copy(cbx2Percent = isChecked)
            "cbxCoefficient" -> _shiftData.value.copy(cbxCoefficient = isChecked)
            else -> _shiftData.value
        }
    }

    // Безопасное преобразование строки в Double
    private fun parseDouble(input: String?): Double {
        return input?.replace(",", ".")?.toDoubleOrNull() ?: 0.0
    }

    // Безопасное преобразование строки в Int
    private fun parseNumber(input: String?): Int {
        return input?.toIntOrNull() ?: 0
    }

    // Очистка данных
    fun clearShiftData() {
        _shiftData.value = Record()  // Очистка объекта
        _inputFields.value = emptyMap()  // Очистка введенных данных
    }

    // Функция сохранения записи в базу данных
    fun saveRecord() {
        viewModelScope.launch {
            repository.addRecord(_shiftData.value) // Сохранение в БД
        }
    }


    // Новый StateFlow для всех записей
    private val _allRecords = MutableStateFlow<List<Record>>(emptyList())
    val allRecords: StateFlow<List<Record>> = _allRecords.asStateFlow()

    // Загрузка записей из репозитория (например, это может быть сетевой запрос или локальная база данных)
    init {
        loadRecords()
    }

    private fun loadRecords() {
        viewModelScope.launch {
            // Собираем данные из Flow, чтобы получить List<Record>
            repository.getAllRecords().collect { records ->
                // Записываем полученные записи в StateFlow
                _allRecords.value = records
            }
        }
    }
}
