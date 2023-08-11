package com.example.in2000_team1.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.in2000_team1.R
import com.example.in2000_team1.ui.theme.OpenSansBold
import com.example.in2000_team1.ui.theme.OrangeGradient


// The overlay with the menu buttons, which appears when you push the hamburgermenubutton
@Composable
fun MenuOverlay(navController: NavController, onClose: () -> Unit){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = OrangeGradient
                )
            )
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val logo = painterResource(R.drawable.logoin2000_4)
        Image(painter = logo, contentDescription = "", modifier = Modifier.size(56.dp) )
        Spacer(modifier = Modifier.padding(24.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Info", color = Color.White, fontSize = 16.sp, fontFamily = OpenSansBold)
            Spacer(modifier = Modifier.padding(4.dp))
            MenuButton(icon = Icons.Default.Info ) {
                navController.navigate("info")
                onClose()
            }
        }
        Spacer(modifier = Modifier.padding(48.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Apparater", color = Color.White, fontSize = 16.sp, fontFamily = OpenSansBold)
            Spacer(modifier = Modifier.padding(4.dp))
            ApplianceButton {
                navController.navigate("applianceList")
                onClose()

            }
        }
        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Hjem", color = Color.White, fontSize = 16.sp, fontFamily = OpenSansBold)
                Spacer(modifier = Modifier.padding(4.dp))
                MenuButton(icon = Icons.Default.Home) {
                    navController.navigate("home")
                    onClose()
                }
            }
            Spacer(modifier = Modifier.padding(80.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sted", color = Color.White, fontSize = 16.sp, fontFamily = OpenSansBold)
                Spacer(modifier = Modifier.padding(4.dp))
                MenuButton(icon = Icons.Default.LocationOn) {
                    navController.navigate("map")
                    onClose()
                }
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Tilbake", color = Color.White, fontSize = 16.sp, fontFamily = OpenSansBold)
            Spacer(modifier = Modifier.padding(4.dp))
            MenuButton(icon = Icons.Default.Close) {
                onClose()
            }
        }

    }

}