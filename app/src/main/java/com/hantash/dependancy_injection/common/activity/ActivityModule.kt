package com.hantash.dependancy_injection.common.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.hantash.dependancy_injection.screens.common.navigator.ScreensNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val activity: AppCompatActivity
) {
    @Provides
    fun activity() = activity

    @Provides
    @ActivityScope
    fun screensNavigator(activity: AppCompatActivity) = ScreensNavigator(activity)

    @Provides
    fun fragmentManager() = activity.supportFragmentManager

    @Provides
    fun layoutInflater(): LayoutInflater = LayoutInflater.from(activity)
}