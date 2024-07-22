package com.hantash.dependancy_injection.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import javax.inject.Inject
import javax.inject.Provider

class MyViewModelFactory @Inject constructor(
    private val viewModelProvider: Provider<MyViewModel>,
    private val viewModelProvider2: Provider<MyViewModel2>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MyViewModel::class.java -> viewModelProvider.get() as T
            MyViewModel2::class.java -> viewModelProvider2.get() as T
            else -> throw RuntimeException("Unsupported ViewModel Type $modelClass")
        }

    }

}