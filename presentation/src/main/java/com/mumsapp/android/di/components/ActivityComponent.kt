package com.mumsapp.android.di.components

import com.mumsapp.android.authentication.*
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.scopes.ActivityScope
import com.mumsapp.android.main.MainActivity
import com.mumsapp.android.profile.AccountSettingsDialog
import com.mumsapp.android.profile.MyProfileFragment
import com.mumsapp.android.profile.UserDetailsSettingsDialog
import com.mumsapp.android.ui.views.LocationWidget
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(activity: AuthActivity)

    fun inject(fragment: AuthMenuFragment)

    fun inject(fragment: SignUpFragment)

    fun inject(fragment: SignInFragment)

    fun inject(fragment: CreatePageFragment)

    fun inject(activity: MainActivity)

    fun inject(fragment: MyProfileFragment)

    fun inject(widget: LocationWidget)

    fun inject(dialog: AccountSettingsDialog)

    fun inject(dialog: UserDetailsSettingsDialog)
}