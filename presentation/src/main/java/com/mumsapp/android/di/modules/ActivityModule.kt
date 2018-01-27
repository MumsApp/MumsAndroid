package com.mumsapp.android.di.modules

import android.support.v4.app.FragmentManager
import com.mumsapp.android.base.BaseActivity
import com.mumsapp.android.base.BaseFragmentActivity
import com.mumsapp.android.di.qualifiers.FragmentContainerId
import com.mumsapp.android.di.scopes.ActivityScope
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
}