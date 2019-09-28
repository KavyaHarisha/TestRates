package com.testrates

import android.app.Activity
import android.app.Application
import com.testrates.di.component.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestRatesApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector


}