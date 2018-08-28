package com.mumsapp.android.di.modules

import com.mumsapp.domain.interactor.chat.GetChatThreadsUseCase
import com.mumsapp.domain.interactor.lobby.*
import com.mumsapp.domain.interactor.shop.*
import com.mumsapp.domain.interactor.transformers.qualifiers.AuthorizationTransformer
import com.mumsapp.domain.interactor.transformers.AuthorizationTransformerProvider
import com.mumsapp.domain.interactor.transformers.UseCaseTransformerProvider
import com.mumsapp.domain.interactor.user.*
import com.mumsapp.domain.repository.AppRepository
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

    @Provides
    @Singleton
    fun providesUpdateAvatarUseCase(userRepository: UserRepository,
                                    @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                    schedulerProvider: SchedulerProvider)
            = UpdateAvatarUseCase(userRepository, transformerProvider, schedulerProvider)

    @Provides
    @Singleton
    fun providesAddUserFriendUseCase(userRepository: UserRepository,
                                       @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                       schedulerProvider: SchedulerProvider)
            = AddUserFriendUseCase(userRepository, transformerProvider, schedulerProvider)

    @Provides
    @Singleton
    fun providesRemoveUserFriendUseCase(userRepository: UserRepository,
                                     @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                     schedulerProvider: SchedulerProvider)
            = RemoveUserFriendUseCase(userRepository, transformerProvider, schedulerProvider)

    @Provides
    @Singleton
    fun providesGetLobbyroomsUseCase(repository: AppRepository,
                                     @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                     schedulerProvider: SchedulerProvider): GetLobbyRoomsUseCase {
        return GetLobbyRoomsUseCase(repository, transformerProvider, schedulerProvider)
    }


    @Provides
    @Singleton
    fun providesSearchLobbyRoomsUseCase(repository: AppRepository,
                                     @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                     schedulerProvider: SchedulerProvider): SearchLobbyRoomsUseCase {
        return SearchLobbyRoomsUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesAddLobbyToFavouriteUseCase(repository: AppRepository,
                                           @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                           schedulerProvider: SchedulerProvider): AddLobbyToFavouriteUseCase {
        return AddLobbyToFavouriteUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesRemoveLobbyFromFavouriteUseCase(repository: AppRepository,
                                           @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                           schedulerProvider: SchedulerProvider): RemoveLobbyFromFavouriteUseCase {
        return RemoveLobbyFromFavouriteUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesJoinLobbyRoomUseCase(repository: AppRepository,
                                                @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                                schedulerProvider: SchedulerProvider): JoinLobbyRoomUseCase {
        return JoinLobbyRoomUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesLeaveLobbyRoomUseCase(repository: AppRepository,
                                     @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                     schedulerProvider: SchedulerProvider): LeaveLobbyRoomUseCase {
        return LeaveLobbyRoomUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesCreateLobbyRoomUseCase(repository: AppRepository,
                                      @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                      schedulerProvider: SchedulerProvider): CreateLobbyRoomUseCase {
        return CreateLobbyRoomUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesDeleteLobbyRoomUseCase(repository: AppRepository,
                                       @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                       schedulerProvider: SchedulerProvider): DeleteLobbyRoomUseCase {
        return DeleteLobbyRoomUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetLobbyRoomTopicsUseCase(repository: AppRepository,
                                       @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                       schedulerProvider: SchedulerProvider): GetLobbyRoomTopicsUseCase {
        return GetLobbyRoomTopicsUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesCreateLobbyRoomTopicUseCase(repository: AppRepository,
                                          @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                          schedulerProvider: SchedulerProvider): CreateLobbyRoomTopicUseCase {
        return CreateLobbyRoomTopicUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetLobbyRoomTopicPostsUseCase(repository: AppRepository,
                                              @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                              schedulerProvider: SchedulerProvider): GetLobbyRoomTopicPostsUseCase {
        return GetLobbyRoomTopicPostsUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesCreateLobbyTopicPostUseCase(repository: AppRepository,
                                            @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                            schedulerProvider: SchedulerProvider): CreateLobbyTopicPostUseCase {
        return CreateLobbyTopicPostUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetProductCategoriesUseCase(repository: AppRepository,
                                            @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                            schedulerProvider: SchedulerProvider): GetProductCategoriesUseCase {
        return GetProductCategoriesUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesSearchShopProductsUseCase(repository: AppRepository,
                                            @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                            schedulerProvider: SchedulerProvider): SearchShopProductsUseCase {
        return SearchShopProductsUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesAddProductToFavouriteUseCase(repository: AppRepository,
                                          @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                          schedulerProvider: SchedulerProvider): AddProductToFavouriteUseCase {
        return AddProductToFavouriteUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesRemoveProductFromFavouriteUseCase(repository: AppRepository,
                                          @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                          schedulerProvider: SchedulerProvider): RemoveProductFromFavouriteUseCase {
        return RemoveProductFromFavouriteUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetShopProductUseCase(repository: AppRepository,
                                                  @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                                  schedulerProvider: SchedulerProvider): GetShopProductUseCase {
        return GetShopProductUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesCreateShopProductUseCase(repository: AppRepository,
                                      @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                      schedulerProvider: SchedulerProvider): CreateShopProductUseCase {
        return CreateShopProductUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesUpdateShopProductUseCase(repository: AppRepository,
                                         @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                         schedulerProvider: SchedulerProvider): UpdateShopProductUseCase {
        return UpdateShopProductUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetMyProductsUseCase(repository: AppRepository,
                                         @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                         schedulerProvider: SchedulerProvider): GetMyProductsUseCase {
        return GetMyProductsUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesGetFavouriteProductsUseCase(repository: AppRepository,
                                     @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                     schedulerProvider: SchedulerProvider): GetFavouriteProductsUseCase {
        return GetFavouriteProductsUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesDeleteProductPhotoUseCase(repository: AppRepository,
                                            @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                            schedulerProvider: SchedulerProvider): DeleteProductPhotoUseCase {
        return DeleteProductPhotoUseCase(repository, transformerProvider, schedulerProvider)
    }

    @Provides
    @Singleton
    fun providesChangeProductThumbnailUseCase(repository: AppRepository,
                                          @AuthorizationTransformer transformerProvider: UseCaseTransformerProvider,
                                          schedulerProvider: SchedulerProvider): ChangeProductThumbnailUseCase {
        return ChangeProductThumbnailUseCase(repository, transformerProvider, schedulerProvider)
    }
}