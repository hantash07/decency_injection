package com.hantash.dependancy_injection.screens

import android.app.Activity
import com.hantash.dependancy_injection.screens.questindetails.QuestionDetailsActivity

class ScreensNavigator(private val activity: Activity) {

    fun toBack() {
        activity.onBackPressed()
    }

    fun toQuestionDetail(questionId: String) {
        QuestionDetailsActivity.start(activity, questionId)
    }

}