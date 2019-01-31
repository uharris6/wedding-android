package com.uharris.wedding.presentation.injection

import android.app.Application
import com.uharris.wedding.presentation.injection.modules.ApplicationModule
import com.uharris.wedding.presentation.injection.modules.DataModule
import com.uharris.wedding.presentation.injection.modules.PresentationModule
import com.uharris.wedding.presentation.injection.modules.UiModule
import com.uharris.wedding.presentation.base.WeddingApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    UiModule::class,
    DataModule::class,
    PresentationModule::class,
    ApplicationModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: WeddingApplication)
}