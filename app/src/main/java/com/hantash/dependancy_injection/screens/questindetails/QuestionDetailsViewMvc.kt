package com.hantash.dependancy_injection.screens.questindetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hantash.dependancy_injection.R
import com.hantash.dependancy_injection.questions.QuestionWithBody
import com.hantash.dependancy_injection.screens.common.image_loader.ImageLoader
import com.hantash.dependancy_injection.screens.common.viewmvc.BaseViewMvc
import com.hantash.dependancy_injection.screens.common.toolbar.MyToolbar

class QuestionDetailsViewMvc (
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val parent: ViewGroup?
): BaseViewMvc<QuestionDetailsViewMvc.Listener>(
    layoutInflater,
    parent,
    R.layout.layout_question_details,
) {
    interface Listener {
        fun onBackButtonClicked()
    }

    private var toolbar: MyToolbar
    private var swipeRefresh: SwipeRefreshLayout
    private var ivUser: ImageView
    private var txtUserName: TextView
    private var txtQuestionBody: TextView

    init {
        ivUser = findViewById(R.id.iv_user)
        txtUserName = findViewById(R.id.txt_user_name)
        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            for (listener in listeners) {
                listener.onBackButtonClicked()
            }
        }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun updateQuestion(question: QuestionWithBody) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txtQuestionBody.text = Html.fromHtml(question.body, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            txtQuestionBody.text = Html.fromHtml(question.body)
        }
        txtUserName.text = question.user.name
        imageLoader.loadImage(question.user.imageUrl, ivUser)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }
}