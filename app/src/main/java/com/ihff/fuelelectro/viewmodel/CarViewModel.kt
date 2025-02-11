package com.ihff.fuelelectro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihff.fuelelectro.data.datastore.UserPreferencesDataStore
import com.ihff.fuelelectro.data.model.Car
import com.ihff.fuelelectro.data.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val repository: CarRepository,
    private val userPreferences: UserPreferencesDataStore
) : ViewModel() {

    // Основное состояние автомобиля
    private val _car = MutableStateFlow(Car())
    val car: StateFlow<Car> = _car.asStateFlow()

    // Временные текстовые значения полей
    private val _inputFields = MutableStateFlow<Map<String, String>>(emptyMap())
    val inputFields: StateFlow<Map<String, String>> = _inputFields.asStateFlow()

    // Список всех автомобилей
    val cars: Flow<List<Car>> = repository.getAllCars()

    // Получаем автомобиль по ID
    suspend fun getCarById(carId: Int) {
        val car = repository.getCarById(carId)
        car?.let {
            _car.value = car
            // Заполняем временные текстовые значения
            _inputFields.value = mapOf(
                "model" to car.model,
                "licensePlate" to car.licensePlate,
                "normaZavodki08" to car.normaZavodki08.toString(),
                "normaZavodki2" to car.normaZavodki2.toString(),
                "rashodSug1" to car.rashodSug1.toString(),
                "rashodSug2" to car.rashodSug2.toString(),
                "rashodSug3" to car.rashodSug3.toString(),
                "rashodBenz1" to car.rashodBenz1.toString(),
                "rashodBenz2" to car.rashodBenz2.toString(),
                "rashodBenz3" to car.rashodBenz3.toString()
            )
        }
    }

    // Добавление нового автомобиля
    fun addCar(car: Car) {
        _car.value = car
        _inputFields.value = mapOf()
    }

    // Обновление временного текстового значения (не конвертируем в Double сразу!)
    fun updateField(fieldName: String, value: String) {
        _inputFields.value = _inputFields.value.toMutableMap().also { it[fieldName] = value }

        // Синхронизируем обновление данных в _car (когда обновляются поля формы)
        _car.value = _car.value.copy(
            model = _inputFields.value["model"] ?: "",
            licensePlate = _inputFields.value["licensePlate"] ?: "",
            normaZavodki08 = parseNumber(_inputFields.value["normaZavodki08"] ?: ""),
            normaZavodki2 = parseNumber(_inputFields.value["normaZavodki2"] ?: ""),
            rashodSug1 = parseNumber(_inputFields.value["rashodSug1"] ?: ""),
            rashodSug2 = parseNumber(_inputFields.value["rashodSug2"] ?: ""),
            rashodSug3 = parseNumber(_inputFields.value["rashodSug3"] ?: ""),
            rashodBenz1 = parseNumber(_inputFields.value["rashodBenz1"] ?: ""),
            rashodBenz2 = parseNumber(_inputFields.value["rashodBenz2"] ?: ""),
            rashodBenz3 = parseNumber(_inputFields.value["rashodBenz3"] ?: "")
        )
    }

    // Конвертация строки в число при сохранении
    private fun parseNumber(input: String): Double {
        return input.replace(',', '.').toDoubleOrNull() ?: 0.0
    }

    // Сохранение изменений в БД
    fun saveCar() {
        viewModelScope.launch {
            val inputValues = _inputFields.value

            val updatedCar = _car.value.copy(
                normaZavodki08 = parseNumber(inputValues["normaZavodki08"] ?: ""),
                normaZavodki2 = parseNumber(inputValues["normaZavodki2"] ?: ""),
                rashodSug1 = parseNumber(inputValues["rashodSug1"] ?: ""),
                rashodSug2 = parseNumber(inputValues["rashodSug2"] ?: ""),
                rashodSug3 = parseNumber(inputValues["rashodSug3"] ?: ""),
                rashodBenz1 = parseNumber(inputValues["rashodBenz1"] ?: ""),
                rashodBenz2 = parseNumber(inputValues["rashodBenz2"] ?: ""),
                rashodBenz3 = parseNumber(inputValues["rashodBenz3"] ?: "")
            )

            if (updatedCar.id == 0) {
                repository.addCar(updatedCar)  // Добавляем новый автомобиль
            } else {
                repository.updateCar(updatedCar)  // Обновляем существующий
            }
        }
    }

    // Выбор автомобиля
    fun selectCar(car: Car) {
        _car.value = car
        _inputFields.value = mapOf()
        viewModelScope.launch {
            userPreferences.saveSelectedCarId(car.id)
        }
    }

    // Удаление автомобиля
    fun deleteCar(car: Car) {
        viewModelScope.launch {
            if (_car.value.id == car.id) {
                _car.value = Car()
                _inputFields.value = mapOf()
            }
            repository.deleteCar(car)
        }
    }

    // Очистка данных
    fun clearCar() {
        _car.value = Car()  // Очистка данных
        _inputFields.value = emptyMap()
    }
}
