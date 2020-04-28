package com.codebase.mvp.infrastructure.module

import com.codebase.mvp.infrastructure.scope.ActivityScope
import com.codebase.mvp.models.LocalCiceroneHolder
import dagger.Module
import dagger.Provides

@Module
class LocalNavigationModule {

    @Provides
    @ActivityScope
    fun provideLocalNavigationHolder() : LocalCiceroneHolder {
        return LocalCiceroneHolder()
    }
}