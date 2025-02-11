package com.ihff.fuelelectro.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddNewRecordViewModel @Inject constructor() : ViewModel() {
    private val _prevWSOdometer = MutableStateFlow("")
    val prevWSOdometer = _prevWSOdometer.asStateFlow()


    private val _prevWSPertrol = MutableStateFlow("")
    val prevWSPetrol = _prevWSPertrol.asStateFlow()

    private val _prevWSLpg = MutableStateFlow("")
    val prevWSLpg = _prevWSLpg.asStateFlow()



    fun updatePrevWSOdometer(prevOdometer: String) {
        _prevWSOdometer.value = prevOdometer
    }

    fun updatePrevWSPetrol(prevPetrol: String){
        _prevWSPertrol.value = prevPetrol
    }

    fun updatePrevWSLpg(prevWSLpg: String){
        _prevWSLpg.value = prevWSLpg
    }
}