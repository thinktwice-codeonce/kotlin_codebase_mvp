package com.bss.codebase.infrastructure.module

import com.bss.codebase.infrastructure.scope.ActivityScope
import com.bss.codebase.models.LocalCiceroneHolder
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