package com.ihff.fuelelectro.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ihff.fuelelectro.data.datastore.UserPreferencesDataStore
import com.ihff.fuelelectro.data.model.Car
import com.ihff.fuelelectro.data.repository.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: CarRepository,
    application: Application,
    private val userPreferencesDataStore: UserPreferencesDataStore
) : ViewModel() {

    // Список автомобилей
    private val _carsList = MutableStateFlow<List<Car>>(emptyList())
    val carList: StateFlow<List<Car>> = _carsList.asStateFlow()

    // Выбранный автомобиль
    private val _selectedCar = MutableStateFlow<Car?>(null)
    val selectedCar: StateFlow<Car?> = _selectedCar.asStateFlow()

    init {
        loadCars()  // Загрузка списка автомобилей
        loadSavedCarId()  // Загрузка сохранённого автомобиля
    }

    // Загрузка всех автомобилей
    private fun loadCars() {
        viewModelScope.launch {
            repository.getAllCars().collect { cars ->
                _carsList.value = cars
                loadSavedCarId()  // Проверяем, есть ли сохранённый автомобиль
            }
        }
    }

    // Загрузка сохранённого ID автомобиля
    private fun loadSavedCarId() {
        viewModelScope.launch {
            userPreferencesDataStore.selectedCarId.collect { carId ->
                carId?.let {
                    val car = _carsList.value.find { it.id == carId }
                    if (car != null) {
                        _selectedCar.value = car  // Если найден, устанавливаем его как выбранный
                    }
                }
            }
        }
    }

    // Метод для выбора автомобиля
    fun selectCar(car: Car) {
        _selectedCar.value = car
        viewModelScope.launch {
            saveSelectedCarId(car.id)  // Сохраняем ID выбранного автомобиля
        }
    }

    // Сохранение ID выбранного автомобиля в UserPreferences
    private suspend fun saveSelectedCarId(carId: Int) {
        userPreferencesDataStore.saveSelectedCarId(carId)
    }

    // Метод для добавления нового автомобиля
    fun addCar(car: Car) {
        viewModelScope.launch {
            repository.addCar(car)  // Добавляем новый автомобиль в репозиторий
        }
    }

    // Метод для удаления автомобиля
    fun deleteCar(car: Car) {
        viewModelScope.launch {
            repository.deleteCar(car)  // Удаляем автомобиль из репозитория
            if (_selectedCar.value == car) {
                _selectedCar.value = null  // Если удаляется выбранный автомобиль, сбрасываем его
                userPreferencesDataStore.clearSelectedCarId()  // Очищаем сохранённый ID
            }
        }
    }
}
