package com.hantash.dependancy_injection.networking

import com.google.gson.annotations.SerializedName
import com.hantash.dependancy_injection.questions.Question

class QuestionsListResponseSchema(@SerializedName("items") val questions: List<Question>)