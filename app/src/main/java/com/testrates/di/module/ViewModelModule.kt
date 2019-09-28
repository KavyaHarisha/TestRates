package com.testrates.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testrates.viewmodel.RateResponseViewModel

import com.testrates.viewmodel.ViewModelFactory
import com.testrates.viewmodel.ViewModelKey

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RateResponseViewModel::class)
    abstract fun bindRateResponseViewModel(rateResponseViewModel:RateResponseViewModel): ViewModel
}
