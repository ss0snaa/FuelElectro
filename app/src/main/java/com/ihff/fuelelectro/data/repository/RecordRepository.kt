package com.ihff.fuelelectro.data.repository

import com.ihff.fuelelectro.data.database.RecordDao
import com.ihff.fuelelectro.data.model.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RecordRepository {
    fun getAllRecords(): Flow<List<Record>>
    suspend fun addRecord(record: Record)
    suspend fun deleteRecord(record: Record)
    suspend fun updateRecord(record: Record)
    suspend fun getRecordById(recordId: Int): Record?
}

class RecordRepositoryImpl @Inject constructor(private val recordDao: RecordDao) : RecordRepository {
    override fun getAllRecords(): Flow<List<Record>> = recordDao.getAllRecords()

    override suspend fun addRecord(record: Record) {
        recordDao.addRecord(record)
    }

    override suspend fun deleteRecord(record: Record) {
        recordDao.deleteRecord(record)
    }

    override suspend fun updateRecord(record: Record) {
        recordDao.updateRecord(record)
    }

    override suspend fun getRecordById(recordId: Int): Record? {
        return recordDao.getRecordById(recordId)
    }
}