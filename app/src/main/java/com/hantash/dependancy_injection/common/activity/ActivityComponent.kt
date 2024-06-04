package com.hantash.dependancy_injection.common.activity

import android.app.Application
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.hantash.dependancy_injection.common.app.AppComponent
import com.hantash.dependancy_injection.common.presentation.PresentationComponent
import com.hantash.dependancy_injection.common.presentation.PresentationModule
import com.hantash.dependancy_injection.networking.StackoverflowApi
import com.hantash.dependancy_injection.screens.ScreensNavigator
import dagger.Component
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