package com.hantash.dependancy_injection

import android.app.Application
import com.hantash.dependancy_injection.common.app.AppComponent
import com.hantash.dependancy_injection.common.app.AppModule

class MyApplication: Application() {

//    public lateinit var appComposition: AppComponent

    override fun onCreate() {
        super.onCreate()
//        appComposition = AppModule()
    }

}