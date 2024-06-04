package com.hantash.dependancy_injection.common.presentation

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.hantash.dependancy_injection.common.activity.ActivityComponent
import com.hantash.dependancy_injection.common.activity.ActivityModule
import com.hantash.dependancy_injection.networking.StackoverflowApi
import com.hantash.dependancy_injection.questions.FetchQuestionDetailUseCase
import com.hantash.dependancy_injection.questions.FetchQuestionsUseCase
import com.hantash.dependancy_injection.screens.common.dialogs.DialogsNavigator
import com.hantash.dependancy_injection.screens.common.image_loader.ImageLoader
import com.hantash.dependancy_injection.screens.common.viewmvc.ViewMvcFactory
import dagger.Module
import dagger.Provides

@Module
class PresentationModule() {

//    @Provides
//    fun viewMvcFactory(layoutInflater: LayoutInflater) = ViewMvcFactory(layoutInflater)

//    @Provides
//    fun dialogsNavigator(fragmentManager: FragmentManager) = DialogsNavigator(fragmentManager)

    @Provides
    fun imageLoader(activity: AppCompatActivity) = ImageLoader(activity)

    @Provides
    fun fetchQuestionsUseCase(stackoverflowApi: StackoverflowApi) = FetchQuestionsUseCase(stackoverflowApi)

    @Provides
    fun fetchQuestionDetailUseCase(stackoverflowApi: StackoverflowApi) = FetchQuestionDetailUseCase(stackoverflowApi)
}