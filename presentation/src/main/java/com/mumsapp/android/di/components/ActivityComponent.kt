package com.mumsapp.android.di.components

import com.mumsapp.android.authentication.*
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.scopes.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: AuthActivity)

    fun inject(fragment: AuthMenuFragment)

    fun inject(fragment: SignUpFragment)

    fun inject(fragment: SignInFragment)

    fun inject(fragment: CreatePageFragment)
}