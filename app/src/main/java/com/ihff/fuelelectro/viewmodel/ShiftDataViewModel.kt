package com.ihff.fuelelectro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihff.fuelelectro.data.datastore.UserPreferencesDataStore
import com.ihff.fuelelectro.data.repository.RecordRepository
import com.ihff.fuelelectro.data.model.Record
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShiftDataViewModel @Inject constructor(
    private val repository: RecordRepository, // Репозиторий для работы с БД
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _record = MutableStateFlow<Record?>(null) // Текущее состояние записи
    val record: StateFlow<Record?> = _record.asStateFlow()

    // Метод для загрузки записи по ID
    fun loadRecordById(recordId: Long) {
        viewModelScope.launch {
            val foundRecord = repository.getRecordById(recordId) // Получаем запись из БД
            _record.value = foundRecord // Обновляем StateFlow
        }
    }
}
