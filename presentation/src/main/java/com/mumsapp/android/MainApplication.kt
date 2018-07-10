package com.mumsapp.android

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.di.components.AppComponent
import com.mumsapp.android.di.components.DaggerAppComponent
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.modules.AppModule
import io.fabric.sdk.android.Fabric
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


class MainApplication : MultiDexApplication() {

    val appComponent: AppComponent by lazy {
        generateAppComponent()
    }

    override fun onCreate() {
        super.onCreate()
        setupFabric()
        setupDefaultFont()

        RxJavaPlugins.setOnObservableAssembly( {
            println("on assembly ${it.javaClass.simpleName}")
            it
        })
        RxJavaPlugins.setOnSingleAssembly( {
            println("on assembly ${it.javaClass.simpleName}")
            it
        })
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

    private fun setupFabric() {
        if (BuildConfig.USE_CRASHLYTICS) {
            Fabric.with(this, Crashlytics())
        }
    }

    private fun setupDefaultFont() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Comic-Sans-MS.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}