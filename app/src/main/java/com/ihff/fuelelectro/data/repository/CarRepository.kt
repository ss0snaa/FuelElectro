package com.ihff.fuelelectro.data.repository

import com.ihff.fuelelectro.data.database.CarDao
import com.ihff.fuelelectro.data.model.Car
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CarRepository {
    fun getAllCars(): Flow<List<Car>>
    suspend fun addCar(car: Car)
    suspend fun deleteCar(car: Car)
    suspend fun updateCar(car: Car)
    suspend fun getCarById(carId: Int): Car?
}

class CarRepositoryImpl @Inject constructor(private val carDao: CarDao) : CarRepository {
    override fun getAllCars(): Flow<List<Car>> = carDao.getAllCars()

    override suspend fun addCar(car: Car) {
        carDao.addCar(car)
    }

    override suspend fun deleteCar(car: Car) {
        carDao.deleteCar(car)
    }

    override suspend fun updateCar(car: Car) {
        carDao.updateCar(car)
    }

    override suspend fun getCarById(carId: Int): Car? {
        return carDao.getCarById(carId)
    }
}