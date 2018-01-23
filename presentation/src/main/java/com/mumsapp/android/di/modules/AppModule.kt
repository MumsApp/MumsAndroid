package com.mumsapp.android.di.modules

import android.content.Context
import com.mumsapp.android.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: MainApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = context.baseContext;
}