package com.testrates.di.component

import android.app.Application
import com.testrates.TestRatesApplication
import com.testrates.di.module.ActivityBindingModule
import com.testrates.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, ActivityBindingModule::class, AndroidSupportInjectionModule::class]
)
interface ApplicationComponent {

    fun inject(application: TestRatesApplication)
    //fun factory(): SampleWorkerFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}