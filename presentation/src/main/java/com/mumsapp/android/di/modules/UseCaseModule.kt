package com.mumsapp.android.di.modules

import com.mumsapp.domain.interactor.user.GetUserProfileUseCase
import com.mumsapp.domain.interactor.user.SignInUseCase
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.interactor.user.SignUpUseCase
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.TokenPersistenceService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun providesSignUpUseCase(userRepository: UserRepository, signInUseCase: SignInUseCase,
                              schedulerProvider: SchedulerProvider): SignUpUseCase {
        return SignUpUseCase(userRepository, signInUseCase, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetUserProfileUseCase(userRepository: UserRepository, schedulerProvider: SchedulerProvider): GetUserProfileUseCase {
        return GetUserProfileUseCase(userRepository, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesSignInUseCase(userRepository: UserRepository, tokenPersistenceService: TokenPersistenceService, getUserProfileUseCase: GetUserProfileUseCase, schedulerProvider: SchedulerProvider) : SignInUseCase {
        return SignInUseCase(userRepository, tokenPersistenceService, getUserProfileUseCase, schedulerProvider)
    }
}