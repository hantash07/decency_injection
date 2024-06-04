package com.hantash.dependancy_injection.common.presentation

import com.hantash.dependancy_injection.common.activity.ActivityComponent
import com.hantash.dependancy_injection.questions.FetchQuestionDetailUseCase
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import com.hantash.dependancy_injection.screens.ScreensNavigator
import com.hantash.dependancy_injection.screens.common.dialogs.DialogsNavigator
import com.hantash.dependancy_injection.screens.common.viewmvc.ViewMvcFactory
import com.hantash.dependancy_injection.screens.questindetails.QuestionDetailsActivity
import com.hantash.dependancy_injection.screens.questionlist.QuestionsListActivity
import dagger.Component
import dagger.Subcomponent

@PresentationScope
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(questionsListActivity: QuestionsListActivity)
    fun inject(questionDetailsActivity: QuestionDetailsActivity)
}