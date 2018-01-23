package com.mumsapp.android.di.components

import android.content.Context
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun plus(module: ActivityModule): ActivityComponent
    fun context(): Context
}