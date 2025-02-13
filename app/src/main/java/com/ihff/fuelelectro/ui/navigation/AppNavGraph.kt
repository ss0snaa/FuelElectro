package com.ihff.fuelelectro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ihff.fuelelectro.ui.screens.AddNewCarScreen
import com.ihff.fuelelectro.ui.screens.AddNewRecordScreen
import com.ihff.fuelelectro.ui.screens.EditCarScreen
import com.ihff.fuelelectro.ui.screens.HomeScreen
import com.ihff.fuelelectro.ui.screens.SettingsScreen
import com.ihff.fuelelectro.ui.screens.ShiftDataScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home_screen") {
        // Главная страница
        composable("home_screen"){
            HomeScreen(navController= navController)
        }

        composable("add_new_record_screen"){
            AddNewRecordScreen(navController = navController)
        }

        composable("settings_screen"){
            SettingsScreen(navController)
        }

        composable("add_new_car") {
            AddNewCarScreen(navController = navController)
        }

        composable("edit_car_screen/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")?.toIntOrNull()
            EditCarScreen(navController, carId)
        }

        composable("shift_data_screen/{recordId}") { backStackEntry ->
            val recordId = backStackEntry.arguments?.getString("recordId")?.toLongOrNull()
            ShiftDataScreen(navController = navController, recordId = recordId)
        }
    }
}
