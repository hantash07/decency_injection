package com.hantash.dependancy_injection.screens.common.navigator

import android.app.Activity
import com.hantash.dependancy_injection.screens.questindetails.QuestionDetailsActivity
import com.hantash.dependancy_injection.screens.viewmodel.ViewModelActivity

class ScreensNavigator(private val activity: Activity) {

    fun toBack() {
        activity.onBackPressed()
    }

    fun toQuestionDetail(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

    fun toViewModelActivity() {
        ViewModelActivity.start(activity)
    }

}