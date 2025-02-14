package com.ihff.fuelelectro.data.model

data class ShiftData(
    val typeOfWork: WorkType = WorkType.DAY,
    val prevOdometer: String? = "",
    val prevPetrol: String? = "",
    val prevLpg: String? = "",

    val nowDistance1: String? = "",
    val nowDistance2: String? = "",
    val nowDistance3: String? = "",
    val nowOdometer: String? = "",

    val isAzs: Boolean = false,
    val azsPetrol: String? = "",
    val azsLpg: String? = "",

    val is2Percent: Boolean = false,
    val is4Percent: Boolean = false,

    val isCoefficient104: Boolean = false,
    val isCoefficient108: Boolean = false,
)

/**
data class ShiftData(
val typeOfWork: WorkType = WorkType.DAY,
val prevOdometer: Int? = null,   // Nullable тип
val prevPetrol: Double? = null,
val prevLpg: Double? = null,

val nowDistance1: Int? = null,
val nowDistance2: Int? = null,
val nowDistance3: Int? = null,
val nowOdometer: Int? = null,

val isAzs: Boolean = false,
val azsPetrol: Double? = null,
val azsLpg: Double? = null,

val is2Percent: Boolean = false,
val is4Percent: Boolean = false,

val isCoefficient104: Boolean = false,
val isCoefficient108: Boolean = false,
)
 */