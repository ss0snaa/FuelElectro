package com.ihff.fuelelectro.viewmodel

import androidx.lifecycle.ViewModel
import com.ihff.fuelelectro.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShiftDataViewModel @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
): ViewModel() {

}