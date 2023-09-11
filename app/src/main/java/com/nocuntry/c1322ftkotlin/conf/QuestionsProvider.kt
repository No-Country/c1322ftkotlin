package com.nocuntry.c1322ftkotlin.conf

import com.nocuntry.c1322ftkotlin.model.QuestionResponse

class QuestionsProvider {
    companion object {
        fun getQuestions(): List<QuestionResponse> {
            return listOf(
                QuestionResponse(
                    "¿que eran las misiones apollo?"
                ),
                QuestionResponse(
                    "¿A que distancia se encuentran la tierra y la luna?"
                ),
                QuestionResponse(
                    "¿A que distancia se encuentra la Estación Espacial Internacional?"
                ),
                QuestionResponse(
                    "¿Cual es La estrella más cercana a nuestro sistema solar?"
                ),
                QuestionResponse(
                    "¿Que es el programa Artemis?"
                )
            )
        }
    }
}