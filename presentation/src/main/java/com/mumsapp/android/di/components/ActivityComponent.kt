package com.mumsapp.android.di.components

import com.mumsapp.android.authentication.*
import com.mumsapp.android.chat.ChatListFragment
import com.mumsapp.android.chat.ChatSettingsDialog
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.scopes.ActivityScope
import com.mumsapp.android.lobby.LobbyFragment
import com.mumsapp.android.main.MainActivity
import com.mumsapp.android.product.MyProductsFragment
import com.mumsapp.android.product.ProductFragment
import com.mumsapp.android.profile.AccountSettingsDialog
import com.mumsapp.android.profile.MyProfileFragment
import com.mumsapp.android.profile.UserDetailsSettingsDialog
import com.mumsapp.android.shop.ShopFilterFragment
import com.mumsapp.android.shop.ShopFragment
import com.mumsapp.android.shop.ShopMenuDialog
import com.mumsapp.android.ui.widgets.LocationWidget
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

    fun inject(fragment: LobbyFragment)

    fun inject(fragment: ChatListFragment)

    fun inject(dialog: ChatSettingsDialog)

    fun inject(fragment: ShopFragment)

    fun inject(dialog: ShopMenuDialog)

    fun inject(fragment: ShopFilterFragment)

    fun inject(fragment: ProductFragment)

    fun inject(fragment: MyProductsFragment)
}