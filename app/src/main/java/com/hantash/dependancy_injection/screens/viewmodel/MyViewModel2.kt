package com.hantash.dependancy_injection.screens.viewmodel

import androidx.lifecycle.*
import com.hantash.dependancy_injection.questions.FetchQuestionDetailUseCase
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import com.hantash.dependancy_injection.questions.Question
import com.hantash.dependancy_injection.questions.QuestionWithBody
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyViewModel2 @Inject constructor(
    private val fetchQuestionsUseCase: FetchQuestionDetailUseCase
): ViewModel() {
    private val _question = MutableLiveData<QuestionWithBody>()
    val questionLiveData: LiveData<QuestionWithBody> = _question

    fun getQuestionDetail(id: String) {
        viewModelScope.launch {
            val result = fetchQuestionsUseCase.fetchQuestionById(id)
            if (result is FetchQuestionDetailUseCase.Result.SUCCESS) {
                _question.value = result.questionDetail
            } else {
                throw RuntimeException("fetch failed")
            }
        }
    }
}