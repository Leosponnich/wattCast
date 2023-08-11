package com.example.in2000_team1.ui.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon


import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.in2000_team1.datalayer.DailyWeatherData
import com.example.in2000_team1.datalayer.Location
import com.example.in2000_team1.R
import com.example.in2000_team1.datalayer.Appliance
import com.example.in2000_team1.datalayer.ApplianceCalc
import com.example.in2000_team1.ui.MainViewModel
import com.example.in2000_team1.ui.theme.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    predictionsAndDays: Map<String, Float>,
    dayAndForecast: Map<String, DailyWeatherData>,
    location: Location,
    currentPrice: Double,
    currentTemp: Double,
    appliances: MutableList<Appliance>,
    lowestPrice: Pair<String, Double>,
    viewModel: MainViewModel,
    navController: NavController
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    // Display the main content when the data is fetched
    var menuOpen by remember { mutableStateOf(false) }

    // Define the SwipeRefresh composable for refreshing data when the user swipes down
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing), onRefresh = { viewModel.update() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(PrimaryColor)
                .fillMaxHeight()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val screenHeight = LocalConfiguration.current.screenHeightDp

            val fontSize = when (screenHeight) {
                in 0..700 -> 25.sp // Small screens
                else -> 40.sp
            }

            val boxModifier = when (screenHeight) {
                in 0..700 -> Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .aspectRatio(1.4f)
                    .fillMaxWidth(0.5f)
                    .background(SecondaryColor1, shape = RoundedCornerShape(8.dp))
                else -> Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .aspectRatio(1f)
                    .fillMaxWidth(0.5f)
                    .background(SecondaryColor1, shape = RoundedCornerShape(16.dp))
            }
            Spacer(modifier = Modifier.padding(16.dp))

            // Location name, with font size decided by screen size
            Text(
                text = location.name,
                color = TextColor1,
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                modifier = Modifier.fillMaxWidth()
            )

            // The two information boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Box with the "right now" information
                Box(boxModifier) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Akkurat nå:", color = Color.White, fontSize = 12.sp)
                        Text("${String.format("%.0f", 100*currentPrice)} øre/kWh", color = Color.White, fontSize = 18.sp, fontFamily = OpenSansBold)
                        Text("$currentTemp °C", color = Color.White, fontSize = 18.sp, fontFamily = OpenSansBold)
                    }
                }

                // Box for the appliances. Can be clicked to navigate to the appliance list
                Box(boxModifier.clickable{navController.navigate("applianceList")}) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(appliances.isEmpty()){
                            Text("Legg til apparater:", color = Color.White, fontSize = 12.sp)
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Add",
                                modifier = Modifier.size(48.dp),
                                tint = Color.White
                            )
                        }
                        else{
                            val applianceCalc = ApplianceCalc()
                            val appliancePriceNow = String.format("%.2f", applianceCalc.calculateApplianceCost(appliances.last(), currentPrice))
                            val appliancePriceLowest = String.format("%.2f", applianceCalc.calculateApplianceCost(appliances.last(), lowestPrice.second))
                            Text(
                                text = "${appliances.last().name}",
                                color = Color.White,
                                fontFamily = OpenSansBold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "${appliances.last().durationMinutes} min",
                                color = Color.White,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Nå: $appliancePriceNow NOK",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = OpenSansBold
                            )
                            Text(
                                text = "${lowestPrice.first}: $appliancePriceLowest NOK",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = OpenSansBold
                            )
                            Icon(
                                Icons.Filled.ArrowForward,
                                contentDescription = "Next",
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                        }

                    }
                }
            }

            // The two charts
            PriceChart(predictions = predictionsAndDays)
            WeatherChart(dayAndForecast = dayAndForecast)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HamburgerMenuButton {
                    menuOpen = true
                }

            }
        }

    }


    if (menuOpen) {
        MenuOverlay(navController = navController, onClose = { menuOpen = false })
    }

}












