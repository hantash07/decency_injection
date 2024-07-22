package com.hantash.dependancy_injection.common.presentation

import com.hantash.dependancy_injection.screens.questindetails.QuestionDetailsActivity
import com.hantash.dependancy_injection.screens.questionlist.QuestionsListActivity
import com.hantash.dependancy_injection.screens.viewmodel.ViewModelActivity
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(questionsListActivity: QuestionsListActivity)
    fun inject(questionDetailsActivity: QuestionDetailsActivity)
    fun inject(viewModelActivity: ViewModelActivity)
}