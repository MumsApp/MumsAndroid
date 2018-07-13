package com.mumsapp.android.di.components

import com.mumsapp.android.authentication.*
import com.mumsapp.android.chat.ChatFragment
import com.mumsapp.android.chat.FriendsChatFragment
import com.mumsapp.android.chat.ChatSettingsDialog
import com.mumsapp.android.chat.ChatThreadFragment
import com.mumsapp.android.common.dialogs.ConfirmationDialog
import com.mumsapp.android.common.dialogs.ConfirmationWithAvatarDialog
import com.mumsapp.android.di.modules.ActivityModule
import com.mumsapp.android.di.scopes.ActivityScope
import com.mumsapp.android.lobby.CreatePostFragment
import com.mumsapp.android.lobby.LobbyCategoryDetailsFragment
import com.mumsapp.android.lobby.LobbyFragment
import com.mumsapp.android.main.MainActivity
import com.mumsapp.android.mums_app_offers.MumsAppOfferDetailsFragment
import com.mumsapp.android.mums_app_offers.MumsAppOffersFragment
import com.mumsapp.android.organisation.OrganisationDetailsFragment
import com.mumsapp.android.product.*
import com.mumsapp.android.profile.*
import com.mumsapp.android.shop.ShopFilterFragment
import com.mumsapp.android.shop.ShopFragment
import com.mumsapp.android.shop.ShopMenuDialog
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.ui.widgets.children_selection.ChildrenSelectionWidget
import com.mumsapp.android.ui.widgets.members.MembersWidget
import com.mumsapp.android.ui.widgets.mums_app_offers.MumsAppOffersWidget
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

    fun inject(fragment: FriendsChatFragment)

    fun inject(dialog: ChatSettingsDialog)

    fun inject(fragment: ShopFragment)

    fun inject(dialog: ShopMenuDialog)

    fun inject(fragment: ShopFilterFragment)

    fun inject(fragment: ProductFragment)

    fun inject(fragment: MyProductsFragment)

    fun inject(fragment: MyWishlistFragment)

    fun inject(dialog: RemoveProductDialog)

    fun inject(fragment: AddProductFragment)

    fun inject(dialog: SelectImageSourceDialog)

    fun inject(dialog: ConfirmationWithAvatarDialog)

    fun inject(fragment: ChatThreadFragment)

    fun inject(fragment: OrganisationDetailsFragment)

    fun inject(widget: MembersWidget)

    fun inject(fragment: MumsAppOffersFragment)

    fun inject(fragment: MumsAppOfferDetailsFragment)

    fun inject(dialog: ConfirmationDialog)

    fun inject(fragment: LobbyCategoryDetailsFragment)

    fun inject(fragment: CreatePostFragment)

    fun inject(fragment: UserProfileFragment)

    fun inject(fragment: ChatFragment)

    fun inject(widget: MumsAppOffersWidget)

    fun inject(widget: ChildrenSelectionWidget)

    fun inject(dialog: AddChildDialog)
}