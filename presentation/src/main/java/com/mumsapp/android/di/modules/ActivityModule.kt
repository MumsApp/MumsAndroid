package com.mumsapp.android.di.modules

import android.app.Activity
import android.support.v4.app.FragmentManager
import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.di.qualifiers.FragmentContainerId
import com.mumsapp.android.di.scopes.ActivityScope
import com.mumsapp.android.profile.AccountSettingsDialog
import com.mumsapp.data.facebook.FacebookLoginObservable
import com.mumsapp.data.repository.FacebookRepositoryImpl
import com.mumsapp.data.repository.GoogleRepositoryImpl
import com.mumsapp.data.utils.ShopFiltersManagerImpl
import com.mumsapp.domain.interactor.user.*
import com.mumsapp.domain.repository.FacebookRepository
import com.mumsapp.domain.repository.GoogleRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.ShopFiltersManager
import com.mumsapp.domain.utils.TokenPersistenceService
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @ActivityScope
    internal fun provideBaseActivity(): BaseActivity {
        return activity
    }

    @Provides
    @ActivityScope
    internal fun providesActivity(activity: BaseActivity) : Activity {
        return activity
    }

    @Provides
    @ActivityScope
    internal fun provideFragmentManager(): FragmentManager {
        return activity.supportFragmentManager
    }

    @Provides
    @ActivityScope
    internal fun provideBaseFragmetActivity(): BaseFragmentActivity {
        return activity as BaseFragmentActivity
    }

    @Provides
    @ActivityScope
    @FragmentContainerId
    internal fun provideFragmentContainerId(activity: BaseFragmentActivity): Int = activity.getFragmentContainerId()

    @Provides
    @ActivityScope
    fun provideFacebookRepository(facebookRepositoryImpl: FacebookRepositoryImpl): FacebookRepository {
        return facebookRepositoryImpl
    }

    @Provides
    @ActivityScope
    fun provideFacebookObservable(facebookLoginObservable: FacebookLoginObservable): FacebookLoginObservable {
        return facebookLoginObservable
    }

    @Provides
    @ActivityScope
    fun providesFacebookSignInUseCase(facebookRepository: FacebookRepository, userRepository: UserRepository,
                                      tokenPersistenceService: TokenPersistenceService, getUserProfileUseCase: GetUserProfileUseCase, schedulerProvider: SchedulerProvider): FacebookSignInUseCase {
        return FacebookSignInUseCase(facebookRepository, userRepository, tokenPersistenceService, getUserProfileUseCase, schedulerProvider)
    }

    @Provides
    @ActivityScope
    fun providesFacebookSignUpUseCase(facebookRepository: FacebookRepository, userRepository: UserRepository,
                                      tokenPersistenceService: TokenPersistenceService, getUserProfileUseCase: GetUserProfileUseCase, schedulerProvider: SchedulerProvider): FacebookSignUpUseCase {
        return FacebookSignUpUseCase(facebookRepository, userRepository, tokenPersistenceService, getUserProfileUseCase, schedulerProvider)
    }

    @Provides
    @ActivityScope
    fun providesGoogleRepository(googleRepositoryImpl: GoogleRepositoryImpl): GoogleRepository {
        return googleRepositoryImpl
    }

    @Provides
    @ActivityScope
    fun providesGoogleSignInUseCase(googleRepository: GoogleRepository, userRepository: UserRepository,
                                      tokenPersistenceService: TokenPersistenceService, getUserProfileUseCase: GetUserProfileUseCase, schedulerProvider: SchedulerProvider): GoogleSignInUseCase {
        return GoogleSignInUseCase(googleRepository, userRepository, tokenPersistenceService, getUserProfileUseCase, schedulerProvider)
    }

    @Provides
    @ActivityScope
    fun providesGoogleSignUpUseCase(googleRepository: GoogleRepository, userRepository: UserRepository,
                                      tokenPersistenceService: TokenPersistenceService, getUserProfileUseCase: GetUserProfileUseCase, schedulerProvider: SchedulerProvider): GoogleSignUpUseCase {
        return GoogleSignUpUseCase(googleRepository, userRepository, tokenPersistenceService, getUserProfileUseCase, schedulerProvider)
    }

    @Provides
    @ActivityScope
    fun providesShopFiltersManager(impl: ShopFiltersManagerImpl): ShopFiltersManager {
        return impl
    }
}