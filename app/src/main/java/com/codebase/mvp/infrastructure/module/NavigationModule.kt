package com.codebase.mvp.infrastructure.module

import com.codebase.mvp.infrastructure.scope.ActivityScope
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
class NavigationModule {
    val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @ActivityScope
    fun provideRouter(): Router {
        return cicerone.router
    }

    @Provides
    @ActivityScope
    fun provideNavigationHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}