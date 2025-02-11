package com.ihff.fuelelectro.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

class UserPreferencesDataStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        private val SELECTED_CAR_ID = intPreferencesKey("selected_car_id")
    }

    val selectedCarId: Flow<Int?> = dataStore.data.map { preferences ->
        preferences[SELECTED_CAR_ID]
    }

    suspend fun saveSelectedCarId(carId: Int) {
        dataStore.edit { preferences ->
            preferences[SELECTED_CAR_ID] = carId
        }
    }

    suspend fun clearSelectedCarId() {
        dataStore.edit { preferences ->
            preferences.remove(SELECTED_CAR_ID)
        }
    }
}