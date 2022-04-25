package com.mohsenmb.mainmodule.ui

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideActionManager(): ActionManager = ActionManager.instance
}