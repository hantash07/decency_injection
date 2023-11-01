package com.hantash.dependancy_injection.questions

import android.util.Log
import com.hantash.dependancy_injection.Constants
import com.hantash.dependancy_injection.networking.StackoverflowApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchQuestionsUseCase {

    sealed class Result {
        class SUCCESS(val questions: List<Question>) : Result()
        object FAILURE : Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    suspend fun fetchLatestQuestions(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.lastActiveQuestions(20)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.SUCCESS(response.body()!!.questions)
                } else {
                    return@withContext Result.FAILURE
                }
            } catch (t: Throwable) {
                Log.d("app-debug", "ERROR: ${t}\n${t.stackTrace}")
                if (t !is CancellationException) {
                    return@withContext Result.FAILURE
                } else {
                    return@withContext Result.FAILURE
                }
            }
        }
    }

}
