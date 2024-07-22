package com.hantash.dependancy_injection.screens.questionlist

import android.os.Bundle
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import com.hantash.dependancy_injection.questions.Question
import com.hantash.dependancy_injection.screens.BaseActivity
import com.hantash.dependancy_injection.screens.common.navigator.ScreensNavigator
import com.hantash.dependancy_injection.screens.common.dialogs.DialogsNavigator
import com.hantash.dependancy_injection.screens.common.viewmvc.ViewMvcFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class QuestionsListActivity : BaseActivity(), QuestionsListViewMvc.Listener {
    @Inject lateinit var viewMvcFactory: ViewMvcFactory
    @Inject lateinit var screensNavigator: ScreensNavigator
    @Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase

    private lateinit var viewMvc: QuestionsListViewMvc
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)

        super.onCreate(savedInstanceState)
        viewMvc = viewMvcFactory.newQuestionsListViewMvc(null)
        setContentView(viewMvc.rootView)
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            try {
                when(val result = fetchQuestionsUseCase.fetchLatestQuestions()) {
                    is FetchQuestionsUseCase.Result.SUCCESS -> {
                        viewMvc.bindData(result.questions)
                        isDataLoaded = true
                    }
                    is FetchQuestionsUseCase.Result.FAILURE -> onFetchFailed()
                }
            } finally {
                viewMvc.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerError()
    }

    override fun onRefreshClicked() {
        fetchQuestions()
    }

    override fun onQuestionClicked(question: Question) {
        screensNavigator.toQuestionDetail(question.id)
    }

    override fun onViewModelClicked() {
        screensNavigator.toViewModelActivity()
    }
}