package com.mumsapp.android.di.modules

import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.interactor.user.signUp.SignUpUseCase
import com.mumsapp.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun providesSignUpUseCase(userRepository: UserRepository, schedulerProvider: SchedulerProvider): SignUpUseCase {
        return SignUpUseCase(userRepository, schedulerProvider)
    }
}