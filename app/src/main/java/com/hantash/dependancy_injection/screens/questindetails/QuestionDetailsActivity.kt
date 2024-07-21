package com.hantash.dependancy_injection.screens.questindetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hantash.dependancy_injection.questions.FetchQuestionDetailUseCase
import com.hantash.dependancy_injection.screens.BaseActivity
import com.hantash.dependancy_injection.screens.common.navigator.ScreensNavigator
import com.hantash.dependancy_injection.screens.common.dialogs.DialogsNavigator
import com.hantash.dependancy_injection.screens.common.viewmvc.ViewMvcFactory
import kotlinx.coroutines.*
import javax.inject.Inject

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsViewMvc.Listener {
    @Inject lateinit var viewMvcFactory: ViewMvcFactory
    @Inject lateinit var screensNavigator: ScreensNavigator
    @Inject lateinit var dialogsNavigator: DialogsNavigator
    @Inject lateinit var fetchQuestionDetailUseCase: FetchQuestionDetailUseCase

    private lateinit var viewMvc: QuestionDetailsViewMvc
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        presentationComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewMvc = viewMvcFactory.newQuestionDetailViewMvc(null)
        setContentView(viewMvc.rootView)

        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            viewMvc.showProgressIndication()
            try {
                when (val result = fetchQuestionDetailUseCase.fetchQuestionById(questionId)) {
                    is FetchQuestionDetailUseCase.Result.SUCCESS -> {
                        val questionBody = result.questionDetail.body
                        viewMvc.updateQuestion(result.questionDetail)
                    }
                    is FetchQuestionDetailUseCase.Result.FAILURE ->  onFetchFailed()
                    else -> {}
                }
            } finally {
                viewMvc.hideProgressIndication()
            }

        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerError()
    }

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

    override fun onBackButtonClicked() {
        screensNavigator.toBack()
    }
}