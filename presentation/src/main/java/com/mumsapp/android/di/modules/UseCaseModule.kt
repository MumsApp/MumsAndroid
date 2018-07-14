package com.mumsapp.android.di.modules

import com.mumsapp.domain.interactor.chat.GetChatThreadsUseCase
import com.mumsapp.domain.interactor.lobby.GetLobbyItemsUseCase
import com.mumsapp.domain.interactor.shop.GetShopItemsUseCase
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.interactor.transformers.AuthorizationTransformerProvider
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
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
    fun providesGetUserProfileUseCase(userRepository: UserRepository, sessionManager: SessionManager,
                                      @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                      schedulerProvider: SchedulerProvider): GetUserProfileUseCase {
        return GetUserProfileUseCase(userRepository, sessionManager, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesSignInUseCase(userRepository: UserRepository, tokenPersistenceService: TokenPersistenceService, getUserProfileUseCase: GetUserProfileUseCase, schedulerProvider: SchedulerProvider): SignInUseCase {
        return SignInUseCase(userRepository, tokenPersistenceService, getUserProfileUseCase, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesSignOutUseCase(sessionManager: SessionManager,
                               tokenPersistenceService: TokenPersistenceService,
                               schedulerProvider: SchedulerProvider): SignOutUserUseCase {
        return SignOutUserUseCase(sessionManager, tokenPersistenceService, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesUpdateLocationUseCase(userRepository: UserRepository,
                                      sessionManager: SessionManager,
                                      @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                      schedulerProvider: SchedulerProvider): UpdateUserLocationUseCase {
        return UpdateUserLocationUseCase(userRepository, sessionManager, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesUpdateUserDetailsUseCase(userRepository: UserRepository,
                                         sessionManager: SessionManager,
                                         @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                         schedulerProvider: SchedulerProvider): UpdateUserDetailsUseCase {
        return UpdateUserDetailsUseCase(userRepository, sessionManager, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    @AuthorizationTransformer
    fun provideAuthorizationTransformer(userRepository: UserRepository, tokenPersistenceService: TokenPersistenceService): UseCaseTransformerProvider {
        return AuthorizationTransformerProvider(userRepository, tokenPersistenceService)
    }

    @Provides
    @Singleton
    fun providesGetLobbyItemsUseCase(schedulerProvider: SchedulerProvider): GetLobbyItemsUseCase {
        return GetLobbyItemsUseCase(schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetShopItemsUseCase(schedulerProvider: SchedulerProvider): GetShopItemsUseCase {
        return GetShopItemsUseCase(schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetChatThreadsUseCase(schedulerProvider: SchedulerProvider) = GetChatThreadsUseCase(schedulerProvider)

    @Provides
    @Singleton
    fun providesCreateUserChildUseCase(userRepository: UserRepository,
                                       @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                       schedulerProvider: SchedulerProvider)
            = CreateUserChildUseCase(userRepository, transformerProvider, schedulerProvider)

    @Provides
    @Singleton
    fun providesUpdateUserChildUseCase(userRepository: UserRepository,
                                       @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                       schedulerProvider: SchedulerProvider)
            = UpdateUserChildUseCase(userRepository, transformerProvider, schedulerProvider)

    @Provides
    @Singleton
    fun providesDeleteUserChildUseCase(userRepository: UserRepository,
                                       @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                       schedulerProvider: SchedulerProvider)
            = DeleteUserChildUseCase(userRepository, transformerProvider, schedulerProvider)
}