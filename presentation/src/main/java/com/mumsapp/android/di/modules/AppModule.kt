package com.mumsapp.android.di.modules

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.gson.Gson
import com.mumsapp.android.BuildConfig
import com.mumsapp.android.MainApplication
import com.mumsapp.android.util.KeyboardHelper
import com.mumsapp.data.net.PublicRestApiProviderImpl
import com.mumsapp.data.repository.ResourceRepositoryImpl
import com.mumsapp.data.repository.UserRepositoryImpl
import com.mumsapp.data.utils.ExceptionDispatcherImpl
import com.mumsapp.data.utils.SchedulerProviderImpl
import com.mumsapp.data.utils.SerializationHelperImpl
import com.mumsapp.data.utils.ValidationHelperImpl
import com.mumsapp.domain.net.PublicRestApi
import com.mumsapp.domain.net.PublicRestApiProvider
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.repository.UserRepository
import com.mumsapp.domain.utils.ExceptionDispatcher
import com.mumsapp.domain.utils.SchedulerProvider
import com.mumsapp.domain.utils.SerializationHelper
import com.mumsapp.domain.utils.ValidationHelper
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
    fun providesRestApiProvider(gson: Gson) : PublicRestApiProvider {
        return PublicRestApiProviderImpl(BuildConfig.API_PUBLIC_URL, gson)
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
}