package com.mumsapp.android.di.components

import android.content.Context
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.modules.AppModule
import com.mumsapp.android.di.modules.UseCaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, UseCaseModule::class))
interface AppComponent {

    fun plus(module: ActivityModule): ActivityComponent
    fun context(): Context
}