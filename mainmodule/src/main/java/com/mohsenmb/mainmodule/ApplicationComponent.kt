package com.mohsenmb.mainmodule

import com.mohsenmb.arch.data.ApiServiceModule
import com.mohsenmb.mainmodule.data.ApiModule
import com.mohsenmb.mainmodule.ui.ApplicationModule
import com.mohsenmb.mainmodule.ui.ContributorsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApiServiceModule::class,
    ApiModule::class,
    ApplicationModule::class,
    ContributorsModule::class
])
interface ApplicationComponent : AndroidInjector<GithubApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubApplication): Builder

        fun build(): ApplicationComponent
    }

}