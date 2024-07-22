package com.hantash.dependancy_injection.screens.viewmodel

import androidx.lifecycle.*
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import com.hantash.dependancy_injection.questions.Question
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionsUseCase
): ViewModel() {
    private val _questions = MutableLiveData<List<Question>>()
    val questionsLiveData: LiveData<List<Question>> = _questions

    init {
        viewModelScope.launch {
            val result = fetchQuestionsUseCase.fetchLatestQuestions()
            if (result is FetchQuestionsUseCase.Result.SUCCESS) {
                _questions.value = result.questions
            } else {
                throw RuntimeException("fetch failed")
            }
        }
    }

}