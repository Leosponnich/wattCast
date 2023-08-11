package com.example.in2000_team1.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.in2000_team1.R
import com.example.in2000_team1.datalayer.Appliance
import com.example.in2000_team1.datalayer.ApplianceCalc
import com.example.in2000_team1.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddApplianceScreen(navController: NavController, onApplianceAdded: (Appliance) -> Unit) {
    // Store the data from the text fields
    var name by remember { mutableStateOf("") }
    var consumption by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }

    // Initialize a snackbar host state and a coroutine scope for showing snackbar messages
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(PrimaryColor)
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Display the header text
        Text(
            "Legg til",
            color = TextColor1,
            textAlign = TextAlign.Start,
            fontSize = 40.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Legg til ditt apparat",
            color = SecondaryColor1,
            textAlign = TextAlign.Start,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))

        // Display the name text field
        Text("Navn", color = TextColor1)
        TextField(
            value = name,
            onValueChange = {name = it},
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            label = {
                Text(
                    text = "Navnet på apparatet",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = true
        )


        Spacer(modifier = Modifier.padding(8.dp))

        // Display the consumption text field
        Text("Forbruk(Watt)", color = TextColor1)
        TextField(
            value = consumption,
            onValueChange = {consumption = it},
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            label = {
                Text(
                    text = "Watt",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = true,
            keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        // Display the duration text field
        Text("Varighet", color = TextColor1)
        TextField(
            value = duration,
            onValueChange = {duration = it},
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
            label = {
                Text(
                    text = "Antall minutter",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            singleLine = true,
            keyboardOptions =  KeyboardOptions(keyboardType = KeyboardType.Number)
        )



        Spacer(modifier = Modifier.padding(12.dp))

        // Host the snackbar in this box, together with a column containing the two buttons
        Box() {
            Box(modifier = Modifier.zIndex(1f)) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddButton {
                    val usageWatts = consumption.toIntOrNull()
                    val durationMinutes = duration.toIntOrNull()
                    if (name.isNotEmpty() && usageWatts != null && durationMinutes != null) {
                        // Add an appliance to the uiState
                        onApplianceAdded(
                            Appliance(
                                name = name,
                                usageWatts = usageWatts,
                                durationMinutes = durationMinutes
                            )
                        )
                        navController.navigate("applianceList")
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("Ugyldig input.")
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))

                // Return  to the appliance list
                ReturnButton {
                    navController.navigate("applianceList")
                }
            }
        }



    }

}
