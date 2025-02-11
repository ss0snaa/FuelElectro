package com.ihff.fuelelectro.data.repository

import com.ihff.fuelelectro.data.database.RecordDao
import com.ihff.fuelelectro.data.model.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecordRepository @Inject constructor(private val recordDao: RecordDao) {
    // Метод для получения всех записей
    val allRecords: Flow<List<Record>> = recordDao.getAllRecords()

    suspend fun addRecord(record: Record){
        recordDao.addRecord(record)
    }

    suspend fun deleteRecord(record: Record){
        recordDao.deleteRecord(record)
    }

    suspend fun updateRecord(record: Record){
        recordDao.updateRecord(record)
    }

}