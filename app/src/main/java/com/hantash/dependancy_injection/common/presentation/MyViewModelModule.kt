package com.hantash.dependancy_injection.common.presentation

import androidx.lifecycle.ViewModel
import com.hantash.dependancy_injection.screens.viewmodel.MyViewModel
import com.hantash.dependancy_injection.screens.viewmodel.MyViewModel2
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class MyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel::class)
    abstract fun myViewModel1(viewModel: MyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyViewModel2::class)
    abstract fun myViewModel2(viewModel: MyViewModel2): ViewModel
}