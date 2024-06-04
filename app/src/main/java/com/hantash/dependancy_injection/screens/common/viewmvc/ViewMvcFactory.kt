package com.hantash.dependancy_injection.screens.common.viewmvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hantash.dependancy_injection.screens.common.image_loader.ImageLoader
import com.hantash.dependancy_injection.screens.questindetails.QuestionDetailsViewMvc
import com.hantash.dependancy_injection.screens.questionlist.QuestionsListViewMvc
import javax.inject.Inject

class ViewMvcFactory @Inject constructor(private val layoutInflater: LayoutInflater, private val imageLoader: ImageLoader) {

    fun newQuestionsListViewMvc(parent: ViewGroup?): QuestionsListViewMvc {
        return QuestionsListViewMvc(layoutInflater, parent)
    }

    fun newQuestionDetailViewMvc(parent: ViewGroup?): QuestionDetailsViewMvc {
        return QuestionDetailsViewMvc(layoutInflater, imageLoader, parent)
    }

}