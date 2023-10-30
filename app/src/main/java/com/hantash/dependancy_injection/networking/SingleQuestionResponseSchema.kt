package com.hantash.dependancy_injection.networking

import com.google.gson.annotations.SerializedName
import com.hantash.dependancy_injection.questions.QuestionWithBody

data class SingleQuestionResponseSchema(@SerializedName("items") val questions: List<QuestionWithBody>) {
    val question: QuestionWithBody get() = questions[0]
}