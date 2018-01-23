package com.mumsapp.android

import android.app.Application
import android.content.Context
import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.di.components.AppComponent
import com.mumsapp.android.di.components.DaggerAppComponent
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.modules.AppModule

class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        generateAppComponent()
    }

    companion object {
        fun getApplication(context: Context): MainApplication {
            return context.applicationContext as MainApplication
        }
    }

    private fun generateAppComponent(): AppComponent {
        return DaggerAppComponent.builder().run {
            appModule(AppModule(this@MainApplication))
            build()
        }
    }

    fun createActivityComponent(activity: BaseActivity): ActivityComponent {
        return appComponent.plus(ActivityModule(activity))
    }
}