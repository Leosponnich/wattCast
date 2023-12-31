package com.example.in2000_team1

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.in2000_team1.datalayer.Appliance
import com.example.in2000_team1.datalayer.DailyWeatherData
import com.example.in2000_team1.datalayer.Location
import com.example.in2000_team1.ui.MainViewModel
import com.example.in2000_team1.ui.composables.MainScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainScreenWithApplianceTest {
    private val appliance = Appliance("Vaskemaskin", 2400, 180)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUpMainScreen() {
        composeTestRule.setContent {
            MainScreen(
                predictionsAndDays = mapOf(
                    "man" to 0.1f,
                    "tir" to 0.2f,
                    "ons" to 0.3f,
                    "tor" to 0.4f,
                    "fre" to 0.5f,
                    "lør" to 0.6f,
                    "søn" to 0.7f
                ),
                dayAndForecast = mapOf(
                    "man" to DailyWeatherData(date = "2023-04-26", airTemperature = 15.0, precipitationSum = 1.0, windSpeed = 5.0),
                    "tir" to DailyWeatherData(date = "2023-04-27", airTemperature = 16.0, precipitationSum = 0.5, windSpeed = 4.0),
                    "ons" to DailyWeatherData(date = "2023-04-28", airTemperature = 17.0, precipitationSum = 0.0, windSpeed = 3.0),
                    "tor" to DailyWeatherData(date = "2023-04-29", airTemperature = 18.0, precipitationSum = 2.0, windSpeed = 6.0),
                    "fre" to DailyWeatherData(date = "2023-04-30", airTemperature = 19.0, precipitationSum = 1.5, windSpeed = 5.5),
                    "lør" to DailyWeatherData(date = "2023-05-01", airTemperature = 20.0, precipitationSum = 0.2, windSpeed = 3.5),
                    "søn" to DailyWeatherData(date = "2023-05-02", airTemperature = 21.0, precipitationSum = 0.8, windSpeed = 4.5)
                ),
                location = Location.Oslo,
                currentPrice = 0.8,
                currentTemp = 22.0,
                appliances = mutableListOf(appliance),
                lowestPrice = Pair("man", 0.1),
                viewModel = MainViewModel(),
                navController = rememberNavController()
            )
        }
    }

    // Test if the appliances information is displayed correctly when appliances list is not empty
    @Test
    fun mainScreenDisplaysApplianceInfoForNonEmptyList() {
        // Set up MainScreen with a non-empty appliances list

        composeTestRule.onNodeWithText(appliance.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("${appliance.durationMinutes} min").assertIsDisplayed()
    }

}