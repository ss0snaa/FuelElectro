package com.ihff.fuelelectro.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.ihff.fuelelectro.data.model.Record

@Dao
interface RecordDao {
    @Query("SELECT * FROM records")
    fun getAllRecords(): Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecord(record: Record)

    @Delete
    suspend fun deleteRecord(record: Record)

    @Update
    suspend fun updateRecord(record: Record)

    @Query("SELECT * FROM records WHERE id = :recordId")
    suspend fun getRecordById(recordId: Int): Record?
}