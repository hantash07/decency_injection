package com.hantash.dependancy_injection.screens

import androidx.appcompat.app.AppCompatActivity
import com.hantash.dependancy_injection.MyApplication
import com.hantash.dependancy_injection.common.activity.ActivityModule
import com.hantash.dependancy_injection.common.app.AppModule
import com.hantash.dependancy_injection.common.app.DaggerAppComponent
import com.hantash.dependancy_injection.common.presentation.PresentationComponent
import com.hantash.dependancy_injection.common.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {
    private val appComponent get() =
        DaggerAppComponent.builder()
        .appModule(AppModule(application as MyApplication))
        .build()

//    private val activityComposition
//        get() = ActivityModule(
//            appComposition,
//            this
//        )

    private val activityComponent get() = appComponent.newActivityComponent(ActivityModule(this))

//    private val activityComponent get() =
//        DaggerActivityComponent.builder()
//            .appComponent(appComponent)
//            .activityModule(ActivityModule(this))
//            .build();

//    protected lateinit var presentationComponent: PresentationComponent

    protected val presentationComponent: PresentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule())
    }
}
