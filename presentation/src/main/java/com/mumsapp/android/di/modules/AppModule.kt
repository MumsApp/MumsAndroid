package com.mumsapp.android.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.inputmethod.InputMethodManager
import com.facebook.CallbackManager
import com.google.gson.Gson
import com.mumsapp.android.BuildConfig
import com.mumsapp.android.MainApplication
import com.mumsapp.android.di.qualifiers.ApplicationId
import com.mumsapp.android.util.KeyboardHelper
import com.mumsapp.data.net.PublicRestApiProviderImpl
import com.mumsapp.data.repository.ImagesRepositoryImpl
import com.mumsapp.data.repository.ResourceRepositoryImpl
import com.mumsapp.data.repository.UserRepositoryImpl
import com.mumsapp.data.utils.*
import com.mumsapp.domain.net.PublicRestApiProvider
import com.mumsapp.domain.repository.ImagesRepository
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: MainApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = context.baseContext

    @Provides
    @Singleton
    fun provideInputMethodManager(context: Context): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    @Provides
    @Singleton
    fun provideKeyboardHelper(imm: InputMethodManager): KeyboardHelper {
        return KeyboardHelper(imm)
    }

    @Provides
    @Singleton
    fun provideResourcesRepository(resourceRepositoryImpl: ResourceRepositoryImpl): ResourceRepository {
        return resourceRepositoryImpl
    }

    @Provides
    @Singleton
    fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository {
        return userRepositoryImpl
    }

    @Provides
    @Singleton
    fun providesSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider {
        return schedulerProviderImpl
    }

    @Provides
    @Singleton
    fun providesValidationHelper(validationHelperImpl: ValidationHelperImpl): ValidationHelper {
        return validationHelperImpl
    }

    @Provides
    @Singleton
    fun providesGson() = Gson()

    @Provides
    @Singleton
    fun providesRestApiProvider(tokenPersistenceService: TokenPersistenceService, gson: Gson) : PublicRestApiProvider {
        return PublicRestApiProviderImpl(tokenPersistenceService, BuildConfig.API_URL, gson)
    }

    @Provides
    @Singleton
    fun providesRestApi(restApiProvider: PublicRestApiProvider) = restApiProvider.provideRestApi()

    @Provides
    @Singleton
    fun providesExceptionDispatcher(exceptionDispatcherImpl: ExceptionDispatcherImpl): ExceptionDispatcher {
        return exceptionDispatcherImpl
    }

    @Provides
    @Singleton
    fun providesSerializationHelper(serializationHelperImpl: SerializationHelperImpl): SerializationHelper {
        return serializationHelperImpl
    }

    @Provides
    @Singleton
    fun providesSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager {
        return sessionManagerImpl
    }

    @Provides
    @Singleton
    fun providesSharedPreferencesManager(sharedPreferencesManagerImpl: SharedPreferencesManagerImpl): SharedPreferencesManager {
        return sharedPreferencesManagerImpl
    }

    @Provides
    @Singleton
    fun providesTokenPersistenceService(tokenPersistenceServiceImpl: TokenPersistenceServiceImpl): TokenPersistenceService {
        return tokenPersistenceServiceImpl
    }

    @Provides
    @Singleton
    internal fun providesSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideCallbackManager() = CallbackManager.Factory.create()

    @Provides
    @Singleton
    fun provideImagesRepository(): ImagesRepository {
        return ImagesRepositoryImpl(BuildConfig.API_URL)
    }

    @Provides
    @Singleton
    fun provideFilesHelper(filesHelperImpl: FilesHelperImpl): FilesHelper = filesHelperImpl

    @Provides
    @Singleton
    @ApplicationId
    fun providesAppId() = BuildConfig.APPLICATION_ID
}