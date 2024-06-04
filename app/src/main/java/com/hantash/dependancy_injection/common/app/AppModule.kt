package com.hantash.dependancy_injection.common.app

import android.app.Application
import androidx.annotation.UiThread
import com.hantash.dependancy_injection.Constants
import com.hantash.dependancy_injection.networking.StackoverflowApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {

    @Provides
    @AppScope
    fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun application() = application

    @Provides
    @AppScope
    fun stackoverflowApi(retrofit: Retrofit): StackoverflowApi = retrofit.create(StackoverflowApi::class.java)
}