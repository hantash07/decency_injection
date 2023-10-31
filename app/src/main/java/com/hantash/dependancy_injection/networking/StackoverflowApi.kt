package com.hantash.dependancy_injection.networking

import com.hantash.dependancy_injection.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackoverflowApi {
    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    suspend fun lastActiveQuestions(@Query("page") pageSize: Int?): Response<QuestionsListResponseSchema>

    @GET("/questions/{questionId}?key=&site=stackoverflow&filter=withbody")
    suspend fun questionDetails(@Path("questionId") questionId: String?): Response<SingleQuestionResponseSchema>
}