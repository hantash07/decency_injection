package com.hantash.dependancy_injection.screens.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import javax.inject.Inject
import javax.inject.Provider

class MyViewModelFactory @Inject constructor(
    private val viewModelProviders : Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = viewModelProviders[modelClass]
        return provider?.get() as? T ?: throw RuntimeException("Unsupported ViewModel Type $modelClass")

//        return when (modelClass) {
//            MyViewModel::class.java -> viewModelProvider.get() as T
//            MyViewModel2::class.java -> viewModelProvider2.get() as T
//            else -> throw RuntimeException("Unsupported ViewModel Type $modelClass")
//        }

    }

}