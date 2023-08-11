package com.example.in2000_team1.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.in2000_team1.R
import com.example.in2000_team1.datalayer.Appliance
import com.example.in2000_team1.datalayer.ApplianceCalc
import com.example.in2000_team1.ui.theme.*

@Composable
fun ApplianceScreen(
    appliances: MutableList<Appliance>, // A list of appliances to display
    currentPrice: Double, // The current price of energy
    lowestPrice: Pair<String, Double>, // The lowest price of energy and its weekday
    navController: NavController, // A NavController for navigating to other screens
    onApplianceRemoved: (Appliance) -> Unit // A callback for when an appliance is removed
) {
    // Initialize a mutable state variable for the menu overlay
    var menuOpen by remember { mutableStateOf(false) }

    // Compose the UI elements for the appliance screen
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(PrimaryColor)
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the header text
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            "Apparater",
            color = TextColor1,
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))

        // Display the list of appliances
        ApplianceList(
            appliances = appliances,
            currentPrice = currentPrice,
            lowestPrice = lowestPrice,
            onApplianceRemoved = onApplianceRemoved
        )

        // Display the add button
        AddButton {
            navController.navigate("addAppliance")
        }

        // Display the hamburger menu button
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HamburgerMenuButton {
                menuOpen = true
            }
        }
    }

    // If the menu is open, display the menu overlay
    if (menuOpen) {
        MenuOverlay(navController = navController, onClose = { menuOpen = false })
    }
}


@Composable
fun ApplianceList(
    appliances: MutableList<Appliance>, // A list of appliances to display
    currentPrice: Double, // The current price of energy
    lowestPrice: Pair<String, Double>, // The lowest price of energy and its weekday
    onApplianceRemoved: (Appliance) -> Unit // A callback for when an appliance is removed
) {
    // Calculate the height of the scroll box based on the screen height. Smaller phones should have a shorter box
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val scrollBoxHeight = when (screenHeight) {
        in 0..700 -> 360.dp // Small screens
        else -> 480.dp
    }

    // Compose the UI elements for the appliance list
    Surface(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, BorderGray),
        modifier = Modifier.padding(8.dp),
        color = Color.White
    ) {
        // Display the list of appliances using a LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(scrollBoxHeight),
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
        ) {
            items(appliances) { appliance ->
                ApplianceBox(
                    appliance = appliance,
                    currentPrice = currentPrice,
                    lowestPrice = lowestPrice,
                    onApplianceRemoved = onApplianceRemoved
                )
            }
        }
    }
}



@Composable
fun ApplianceBox(
    appliance: Appliance, // The appliance to display
    currentPrice: Double, // The current price of energy
    lowestPrice: Pair<String, Double>, // The lowest price of energy and its weekday
    onApplianceRemoved: (Appliance) -> Unit // A callback for when the appliance is removed
) {
    // Initialize a calculator for appliance cost
    val applianceCalc = ApplianceCalc()

    // Calculate the appliance cost at the current price and the lowest price
    val appliancePriceNow = String.format("%.2f", applianceCalc.calculateApplianceCost(appliance, currentPrice))
    val appliancePriceLowest = String.format("%.2f", applianceCalc.calculateApplianceCost(appliance, lowestPrice.second))

    // Compose the UI elements for the appliance box
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(4.dp))
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp))
            .background(
                brush = Brush.verticalGradient(
                    WindGradient
                )
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display the appliance name, usage, and duration
            Text(
                text = "${appliance.name} (${appliance.usageWatts}W, ${appliance.durationMinutes} min)",
                color = Color.White,
                fontFamily = OpenSansBold,
                fontSize = 14.sp
            )

            // Display the appliance cost at the current price and the lowest price
            Text(
                text = "Pris akkurat nå: $appliancePriceNow NOK",
                color = Color.White,
                fontSize = 12.sp
            )
            Text(
                text = "Lavest (${lowestPrice.first}): $appliancePriceLowest NOK",
                color = Color.White,
                fontSize = 12.sp
            )
        }

        // Display the delete button
        IconButton(
            onClick = { onApplianceRemoved(appliance) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp, end = 4.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Delete", tint = Color.White)
        }
    }
}



