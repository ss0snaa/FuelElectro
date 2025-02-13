package com.ihff.fuelelectro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val model: String = "",
    val licensePlate: String = "",
    val normaZavodki08: Double? = 0.0,
    val normaZavodki2: Double? = 0.0,
    val normaZavodki4: Double? = 0.0,
    val rashodSug1: Double? = 0.0,
    val rashodSug2: Double? = 0.0,
    val rashodSug3: Double? = 0.0,
    val rashodBenz1: Double? = 0.0,
    val rashodBenz2: Double? = 0.0,
    val rashodBenz3: Double? = 0.0
)