package com.example.in2000_team1.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.in2000_team1.ui.theme.BorderGray
import com.example.in2000_team1.ui.theme.PrimaryColor

@Composable
fun InformationScreen(navController: NavController) {
    // Get the screen height in dp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    // If the phone is a small one, make the box height shorter
    val scrollBoxHeight = when (screenHeight) {
        in 0..700 -> 400.dp // Small screens
        else -> 480.dp
    }

    var menuOpen by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(PrimaryColor)
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Data class to hold the Q&A
        data class QnA(val question: String, val answer: String)

        // List of Q&A
        val qnaList = listOf(
            QnA("Hvordan estimerer vi fremtidige priser?", "Vi bruker historisk vær- og prisdata i en maskinlæringsmodell(multippel lineær regresjon) for å estimere fremtidige priser. Temperatur, regn og vind er med i beregningen. Estimatene vi viser er gjennomsnittlige priser for hver dag."),
            QnA("Hvordan oppdaterer jeg til de nyeste dataene?", "På hovedskjermen, dra fingeren ned fra toppen. Siden vil lastes inn på nytt med de nyeste dataene."),
            QnA("Hvordan kan jeg bruke denne kalkulatoren til å spare penger?",
                "Du kan velge å spare bruken av apparatene dine til de dagene med lavest estimert strømpris. Du kan bruke denne kalkulatoren for å identifisere enheter eller apparater som koster mye penger å bruke og gjøre endringer for å redusere bruken." +
                        " For eksempel kan du bytte til energieffektive lyspærer eller trekke ut elektronikk når de ikke er i bruk. "
            ),
            QnA("Hvordan kan jeg fjerne et apparat?", "Trykk på krysset i høyre hjørne."),
        )

        // Use the list to populate the LazyColumn
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        ) {
            //title
            Text(
                text = "Informasjon",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 32.sp,
                fontWeight = FontWeight(400),
                color = Color(2, 48, 71)
            )
            //Subtitle
            Text(
                text = "Ofte stilte spørsmål",
                modifier = Modifier.padding(bottom = 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight(500),
                color = Color(2, 48, 71)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(1.dp, BorderGray),
            modifier = Modifier.padding(8.dp),
            color = Color.White
        ) {
            LazyColumn(
                modifier = Modifier
                    .height(scrollBoxHeight)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp, horizontal = 8.dp)
            ) {
                items(qnaList.size) { index ->
                    val qna = qnaList[index]
                    Column(
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        //Q&A
                        Question(question = qna.question)
                        Answer(answer = qna.answer)
                    }
                }
            }
        }


        Spacer(modifier = Modifier.padding(16.dp))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HamburgerMenuButton {
                menuOpen = true

            }

        }

    }


    if (menuOpen) {
        MenuOverlay(navController = navController, onClose = { menuOpen = false })
    }
}

@Composable
fun Question(question: String){
    Text(text = question,
        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
        fontSize = 16.sp,
        fontWeight = FontWeight(500),
        color = Color(53,88,107)
    )
}

@Composable
fun Answer(answer:String){
    Text(text = answer,
        fontSize = 12.sp,
        fontWeight = FontWeight(500),
        color = Color(255,153,0)
    )
}
