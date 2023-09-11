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
import com.nocuntry.c1322ftkotlin.conf.Biography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IAViewModel : ViewModel() {

    var translateText: String by mutableStateOf("")

    var Info: String by mutableStateOf("")

    var loading: Boolean by mutableStateOf(false)

    var error: Boolean by mutableStateOf(false)

    var isReady: Boolean by mutableStateOf(false)

    var welcomeQuestion: Boolean by mutableStateOf(true)

    var newQuestion = ""




    private var openAI = OpenAI(token = Env.OPENIA_KEY, logging = LoggingConfig(LogLevel.All))

    private val biography = Biography.Biography

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


    @OptIn(BetaOpenAI::class)
    fun MoreInfo(text: String, question: String) = viewModelScope.launch {

        startLoading()

        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(Conf.GPT_MODEL),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "tu nombre es HAL, tienes que ser amable, si es la primera pregunta debes saludar a la persona y tratarla de manera correcta"
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "Tu tarea es extraer lo mas importante de este texto $text para poder responder preguntas de manera simple, didactica pudiendo ampliar la informacion con estos datos si es necesario $biography"
                ),
                ChatMessage(
                    role = ChatRole.System,
                    content = "en caso de que existan preguntas anteriores este texto $question tambien puedes usarlas para responder a la pregunta"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "Responde esta pregunta de manera simple, didactica y resumida en base al texto: $question"
                )
            )
        )

        Info =
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



