package com.hantash.dependancy_injection.common.activity

import com.hantash.dependancy_injection.common.presentation.PresentationComponent
import com.hantash.dependancy_injection.common.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule): PresentationComponent

//    fun activity() : AppCompatActivity
//
//    fun application(): Application
//
//    fun screensNavigator(): ScreensNavigator
//
//    fun fragmentManager(): FragmentManager
//
//    fun layoutInflater(): LayoutInflater
//
//    fun stackoverflowApi(): StackoverflowApi
}