package com.ihff.fuelelectro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class Record(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val dateRecord: Long = System.currentTimeMillis(),

    val typeOfWork: WorkType = WorkType.DAY,

    val nowWSD1: Int = 0,
    val nowWSD2: Int = 0,
    val nowWSD3: Int = 0,
    val nowWSOdometer: Int = 0,

    val azsPetrol: Double = 0.0,
    val azsLpg: Double = 0.0,

    val cbxAzs: Boolean = false,
    val cbx2Percent: Boolean = false,
    val cbx4Percent: Boolean = false,
    val cbxCoefficient104: Boolean = false,
    val cbxCoefficient108: Boolean = false,

    val prevWSOdometer: Int = 0,
    val prevWSPetrol: Double = 0.0,
    val prevWSLpg: Double = 0.0
)
