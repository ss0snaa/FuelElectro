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

    private val _nowWSD1 = MutableStateFlow("")
    val nowWSD1: StateFlow<String> = _nowWSD1.asStateFlow()
    fun updateNowWSD1(newValue: String) {
        _nowWSD1.value = newValue
    }

    private val _nowWSD2 = MutableStateFlow("")
    val nowWSD2: StateFlow<String> = _nowWSD2.asStateFlow()
    fun updateNowWSD2(newValue: String) {
        _nowWSD2.value = newValue
    }

    private val _nowWSD3 = MutableStateFlow("")
    val nowWSD3: StateFlow<String> = _nowWSD3.asStateFlow()
    fun updateNowWSD3(newValue: String) {
        _nowWSD3.value = newValue
    }

    private val _nowWSOdometer = MutableStateFlow("")
    val nowWSDOdometer: StateFlow<String> = _nowWSOdometer.asStateFlow()
    fun updateNowWSDOdometer(newValue: String) {
        _nowWSOdometer.value = newValue
    }

    private val _cbxAzs = MutableStateFlow(false)
    val cbxAzs: StateFlow<Boolean> = _cbxAzs.asStateFlow()
    fun updateCbxAzs(newValue: Boolean) {
        _cbxAzs.value = newValue
    }

    private val _cbx2Percent = MutableStateFlow(false)
    val cbx2Percent: StateFlow<Boolean> = _cbx2Percent.asStateFlow()
    fun updateCbx2Percent(newValue: Boolean) {
        _cbx2Percent.value = newValue
    }

    private val _cbx4Percent = MutableStateFlow(false)
    val cbx4Percent: StateFlow<Boolean> = _cbx4Percent.asStateFlow()
    fun updateCbx4Percent(newValue: Boolean) {
        _cbx4Percent.value = newValue
    }

    private val _cbxCoefficient104 = MutableStateFlow(false)
    val cbxCoefficient104: StateFlow<Boolean> = _cbxCoefficient104.asStateFlow()
    fun updateCbxCoefficient104(newValue: Boolean) {
        _cbxCoefficient104.value = newValue
    }

    private val _cbxCoefficient108 = MutableStateFlow(false)
    val cbxCoefficient108: StateFlow<Boolean> = _cbxCoefficient108.asStateFlow()
    fun updateCbxCoefficient108(newValue: Boolean) {
        _cbxCoefficient108.value = newValue
    }

    private val _azsPetrol = MutableStateFlow("")
    val azsPetrol: StateFlow<String> = _azsPetrol.asStateFlow()
    fun updateAzsPetrol(newValue: String) {
        _azsPetrol.value = newValue
    }

    private val _azsLpg = MutableStateFlow("")
    val azsLpg: StateFlow<String> = _azsLpg.asStateFlow()
    fun updateAzsLpg(newValue: String){
        _azsLpg.value = newValue
    }

    fun saveRecord() {
        Log.d(
            TAG, "saveRecord: " +
                    "Type Of Works Shift: ${typeOfWorksShift.value}" +
                    "Prev WS Odometer: ${prevWSOdometer.value}" +
                    "Prev WS Petrol: ${prevWSPetrol.value}" +
                    "Prev WS LPG: ${prevWSLpg.value}" +
                    "Now WS D1: ${nowWSD1.value}" +
                    "Now WS D2: ${nowWSD2.value}" +
                    "Now WS D3: ${nowWSD3.value}" +
                    "Now WS Odometer: ${nowWSDOdometer.value}" +
                    "CBX AZS: ${cbxAzs.value}" +
                    "AZS Petrol: ${azsPetrol.value}" +
                    "AZS LPG: ${azsLpg.value}" +
                    "CBX 2%: ${cbx2Percent.value}" +
                    "CBX 4%: ${cbx4Percent.value}" +
                    "CBX Coefficient 1.04: ${cbxCoefficient104.value}" +
                    "CBX Coefficient 1.08: ${cbxCoefficient108.value}"
        )

        viewModelScope.launch {
            val recordToSave = Record(
                typeOfWork = typeOfWorksShift.value,

                prevWSOdometer = parseNumber(prevWSOdometer.value),
                prevWSPetrol = parseDouble(prevWSPetrol.value),
                prevWSLpg = parseDouble(prevWSLpg.value),

                nowWSD1 = parseNumber(nowWSD1.value),
                nowWSD2 = parseNumber(nowWSD2.value),
                nowWSD3 = parseNumber(nowWSD3.value),
                nowWSOdometer = parseNumber(nowWSDOdometer.value),

                cbxAzs = cbxAzs.value,
                azsPetrol = parseDouble(azsPetrol.value),
                azsLpg = parseDouble(azsLpg.value),
                cbx2Percent = cbx2Percent.value,
                cbx4Percent = cbx4Percent.value,
                cbxCoefficient104 = cbxCoefficient104.value,
                cbxCoefficient108 = cbxCoefficient108.value
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
