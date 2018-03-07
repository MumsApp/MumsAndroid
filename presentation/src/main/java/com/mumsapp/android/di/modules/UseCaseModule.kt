package com.mumsapp.android.di.modules

import com.mumsapp.domain.interactor.user.*
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SessionManager
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

    @Provides
    @Singleton
    fun providesSignOutUseCase(sessionManager: SessionManager,
                               tokenPersistenceService: TokenPersistenceService,
                               schedulerProvider: SchedulerProvider) : SignOutUserUseCase {
        return SignOutUserUseCase(sessionManager, tokenPersistenceService, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesUpdateLocationUseCase(userRepository: UserRepository,
                                      sessionManager: SessionManager,
                                      schedulerProvider: SchedulerProvider) : UpdateUserLocationUseCase {
        return UpdateUserLocationUseCase(userRepository, sessionManager, schedulerProvider)
    }
}