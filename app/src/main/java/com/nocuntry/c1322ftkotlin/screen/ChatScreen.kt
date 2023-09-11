package com.nocuntry.c1322ftkotlin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.matias141201.NASAImage.components.ChatCard
import com.nocuntry.c1322ftkotlin.IAViewModel
import com.nocuntry.c1322ftkotlin.R
import com.nocuntry.c1322ftkotlin.components.QuestionCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
    explanation: String?,
    viewModel: IAViewModel
) {
    val question = rememberSaveable {
        mutableStateOf("")
    }

    val chatList = remember { mutableStateListOf<Pair<String, Color>>() }

    val focusManager = LocalFocusManager.current

    val previousQuestionsAndAnswers = remember { mutableListOf<Pair<String, String>>() }

    var welcome by remember {
        mutableStateOf(true)
    }


    Column {

        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.chatgptlogo),
                        contentDescription = "Chat GPT logo",
                        tint = Color.Unspecified
                    )
                }
            },
            title = {
                Text(
                    "HAL"
                )
            },
            modifier = Modifier.background(Color.Gray)
        )



        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            items(chatList) { chatItem ->
                ChatCard(chatItem.first, chatItem.second)
            }

            item {

                if (viewModel.welcomeQuestion) {
                    QuestionCard(explanation.toString(), viewModel)

                }

                if (welcome) {
                    chatList.add(
                        Pair(
                            "Hola, mi nombre es HAL, soy una inteligencia artificial y estoy aquí para responder todas tus dudas astronómicas.",
                            Color.Black
                        )
                    )
                    welcome = false
                }

                if (viewModel.isReady) {
                    chatList.add(Pair(viewModel.newQuestion, Color.Gray))
                    viewModel.isReady = false
                }

                AnimationChat(isLoading = viewModel.loading, translateComplete = {
                    if (viewModel.Info.isNotEmpty()) {
                        val newAnswer = viewModel.Info

                        if (!previousQuestionsAndAnswers.contains(
                                Pair(
                                    viewModel.newQuestion,
                                    newAnswer
                                )
                            )
                        ) {

                            chatList.add(Pair(newAnswer, Color.Black))

                            previousQuestionsAndAnswers.add(Pair(viewModel.newQuestion, newAnswer))
                        }
                    }
                })
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {

                        TextField(
                            value = question.value,
                            onValueChange = { question.value = it },
                            label = { Text("Escribe aqui") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF000000)),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color(0xFF070707),
                                focusedIndicatorColor = Color(0xFF000000),
                                unfocusedIndicatorColor = Color(0xFF000000)
                            )
                        )

                        IconButton(onClick = {

                            viewModel.newQuestion = question.value
                            if (viewModel.newQuestion.isNotBlank()) {
                                viewModel.MoreInfo(explanation.toString(), viewModel.newQuestion)
                                viewModel.newQuestion = question.value
                                viewModel.isReady = true
                                question.value = ""
                                focusManager.clearFocus()
                            }

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_send_24),
                                contentDescription = "send button",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}




