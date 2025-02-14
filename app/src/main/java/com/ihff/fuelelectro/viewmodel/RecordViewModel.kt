package com.ihff.fuelelectro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihff.fuelelectro.data.repository.RecordRepository
import com.ihff.fuelelectro.domain.ShiftCalculationUseCase
import com.ihff.fuelelectro.data.model.Record
import com.ihff.fuelelectro.data.model.ShiftData
import com.ihff.fuelelectro.data.model.WorkType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val repository: RecordRepository,
    private val shiftCalculationUseCase: ShiftCalculationUseCase
) : ViewModel() {

    // Хранение списка записей
    private val _records = MutableStateFlow<List<Record>>(emptyList())
    val records: StateFlow<List<Record>> = _records.asStateFlow()

    // Хранение данных о смене
    private val _shiftData = MutableStateFlow(ShiftData()) // Изначально пустой объект ShiftData
    val shiftData: StateFlow<ShiftData> = _shiftData.asStateFlow()

    // Обновление данных по смене (обновление всего объекта ShiftData)
    fun updateShiftData(newShiftData: ShiftData) {
        _shiftData.value = newShiftData
    }

    // Сохранение записи
    fun saveRecord() {
        viewModelScope.launch {
            val recordToSave = Record(
                typeOfWork = shiftData.value.typeOfWork,

                prevWSOdometer = parseNumber(shiftData.value.prevOdometer),
                prevWSPetrol = parseDouble(shiftData.value.prevPetrol),
                prevWSLpg = parseDouble(shiftData.value.prevLpg),

                nowWSD1 = parseNumber(shiftData.value.nowDistance1),
                nowWSD2 = parseNumber(shiftData.value.nowDistance2),
                nowWSD3 = parseNumber(shiftData.value.nowDistance3),
                nowWSOdometer = parseNumber(shiftData.value.nowOdometer),

                cbxAzs = shiftData.value.isAzs,
                azsPetrol = parseDouble(shiftData.value.azsPetrol),
                azsLpg = parseDouble(shiftData.value.azsLpg),

                cbx2Percent = shiftData.value.is2Percent,
                cbx4Percent = shiftData.value.is4Percent,

                cbxCoefficient104 = shiftData.value.isCoefficient104,
                cbxCoefficient108 = shiftData.value.isCoefficient108
            )
            repository.addRecord(recordToSave)
        }
    }


    // Безопасное преобразование строки в Double
    private fun parseDouble(input: String?): Double = input?.replace(",", ".")?.toDoubleOrNull() ?: 0.0

    // Безопасное преобразование строки в Int
    private fun parseNumber(input: String?): Int = input?.toIntOrNull() ?: 0


    // Инициализация данных, например, загрузка всех записей
    init {
        viewModelScope.launch {
            repository.getAllRecords().collect { records ->
                _records.value = records
            }
        }
    }
}
