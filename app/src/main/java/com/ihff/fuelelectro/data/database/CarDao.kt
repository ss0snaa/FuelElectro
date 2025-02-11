package com.ihff.fuelelectro.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ihff.fuelelectro.data.model.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Query("SELECT * FROM cars WHERE id = :carId")
    suspend fun getCarById(carId: Int): Car?
}