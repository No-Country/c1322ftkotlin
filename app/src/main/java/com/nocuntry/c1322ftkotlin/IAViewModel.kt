package com.nocuntry.c1322ftkotlin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.logging.LogLevel
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.LoggingConfig
import com.aallam.openai.client.OpenAI
import com.matias141201.NASAImage.conf.Conf
import com.matias141201.NASAImage.conf.Env
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IAViewModel : ViewModel() {

    var translateText: String by mutableStateOf("")

    var loading: Boolean by mutableStateOf(false)

    var error: Boolean by mutableStateOf(false)


    private var openAI = OpenAI(token = Env.OPENIA_KEY, logging = LoggingConfig(LogLevel.All))

    @OptIn(BetaOpenAI::class)
    fun translate(text: String) = viewModelScope.launch {

        startLoading()

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(Conf.GPT_MODEL),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "Eres un asistente especializado en traduciones del ingles al español de la forma mas precisa posible"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "Traduce este texto de ingles a español: $text"
                )
            )
        )

        translateText =
            openAI.chatCompletion(chatCompletionRequest).choices.first().message?.content.toString()

        endLoading()
    }


    private fun startLoading() {
        loading = true
        error = false
    }

    private fun endLoading() {
        loading = false
    }


}


