package com.ihff.fuelelectro.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihff.fuelelectro.data.repository.RecordRepository
import com.ihff.fuelelectro.domain.ShiftCalculationUseCase
import com.ihff.fuelelectro.data.model.Record
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

    private val TAG: String = "ss0snaa"

    private val _records = MutableStateFlow<List<Record>>(emptyList())
    val records: StateFlow<List<Record>> = _records.asStateFlow()

    private val _typeOfWorksShift = MutableStateFlow("D")
    val typeOfWorksShift: StateFlow<String> = _typeOfWorksShift.asStateFlow()

    fun updateTypeOfWorkShift(newValue: String) {
        _typeOfWorksShift.value = newValue
    }

    private val _prevWSOdometer = MutableStateFlow("")
    val prevWSOdometer: StateFlow<String> = _prevWSOdometer.asStateFlow()

    fun updatePrevWSOdometer(newValue: String) {
        _prevWSOdometer.value = newValue
    }

    private val _prevWSPetrol = MutableStateFlow("")
    val prevWSPetrol: StateFlow<String> = _prevWSPetrol.asStateFlow()
    fun updatePrevWSPetrol(newValue: String) {
        _prevWSPetrol.value = newValue

    }

    private val _prevWSLpg = MutableStateFlow("")
    val prevWSLpg: StateFlow<String> = _prevWSLpg.asStateFlow()
    fun updatePrevWSLpg(newValue: String) {
        _prevWSLpg.value = newValue
    }

    fun saveRecord() {
        Log.d(
            TAG, "saveRecord: " +
                    "Type Of Works Shift: ${typeOfWorksShift.value}" +
                    "Prev WS Odometer: ${prevWSOdometer.value}" +
                    "Prev WS Petrol: ${prevWSPetrol.value}" +
                    "Prev WS LPG: ${prevWSLpg.value}"
        )

        viewModelScope.launch {
            val recordToSave = Record(
                typeOfWork = typeOfWorksShift.value,
                prevWSOdometer = parseNumber(prevWSOdometer.value),
                prevWSPetrol = parseDouble(prevWSPetrol.value),
                prevWSLpg = parseDouble(prevWSLpg.value)
            )
            repository.addRecord(recordToSave)
        }
    }

    init {
        viewModelScope.launch {
            repository.getAllRecords().collect { records ->
                _records.value = records
            }
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
}
