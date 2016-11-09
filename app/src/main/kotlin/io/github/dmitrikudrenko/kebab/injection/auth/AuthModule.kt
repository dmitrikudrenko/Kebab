package io.github.dmitrikudrenko.kebab.injection.auth

import dagger.Module
import dagger.Provides
import io.github.dmitrikudrenko.kebab.ui.auth.presenter.AuthPresenter
import io.github.dmitrikudrenko.kebab.ui.auth.presenter.AuthPresenterImpl
import javax.inject.Singleton


@Module
class AuthModule {
    @Provides
    @Singleton
    fun providePresenter(): AuthPresenter {
        return AuthPresenterImpl()
    }
}