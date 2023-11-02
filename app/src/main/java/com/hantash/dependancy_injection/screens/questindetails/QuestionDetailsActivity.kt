package com.hantash.dependancy_injection.screens.questindetails

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hantash.dependancy_injection.Constants
import com.hantash.dependancy_injection.MyApplication
import com.hantash.dependancy_injection.R
import com.hantash.dependancy_injection.networking.StackoverflowApi
import com.hantash.dependancy_injection.questions.FetchQuestionDetailUseCase
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import com.hantash.dependancy_injection.screens.ScreensNavigator
import com.hantash.dependancy_injection.screens.common.dialogs.DialogsNavigator
import com.hantash.dependancy_injection.screens.common.dialogs.ServerErrorDialogFragment
import com.hantash.dependancy_injection.screens.common.toolbar.MyToolbar
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionDetailsActivity : AppCompatActivity(), QuestionDetailsViewMvc.Listener {
    private lateinit var viewMvc: QuestionDetailsViewMvc
    private lateinit var fetchQuestionDetailUseCase: FetchQuestionDetailUseCase
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screensNavigator: ScreensNavigator
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewMvc = QuestionDetailsViewMvc(LayoutInflater.from(this), null)
        setContentView(viewMvc.rootView)

        fetchQuestionDetailUseCase = FetchQuestionDetailUseCase((application as MyApplication).stackoverflowApi)
        dialogsNavigator = DialogsNavigator(supportFragmentManager)
        screensNavigator = ScreensNavigator(this)

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
                        viewMvc.updateQuestion(questionBody)
                    }
                    is FetchQuestionDetailUseCase.Result.FAILURE ->  onFetchFailed()
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