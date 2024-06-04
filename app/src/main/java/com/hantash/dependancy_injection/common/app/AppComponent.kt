package com.hantash.dependancy_injection.common.app

import com.hantash.dependancy_injection.common.activity.ActivityComponent
import com.hantash.dependancy_injection.common.activity.ActivityModule
import com.hantash.dependancy_injection.networking.StackoverflowApi
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent

//    fun application(): Application
//    fun stackoverflowApi(): StackoverflowApi
}