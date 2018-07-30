package com.mumsapp.android.navigation

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.FragmentManager
import com.google.android.gms.maps.SupportMapFragment
import com.mumsapp.android.authentication.AuthMenuFragment
import com.mumsapp.android.authentication.CreatePageFragment
import com.mumsapp.android.authentication.SignInFragment
import com.mumsapp.android.authentication.SignUpFragment
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.chat.ChatFragment
import com.mumsapp.android.chat.FriendsChatFragment
import com.mumsapp.android.chat.ChatThreadFragment
import com.mumsapp.android.di.qualifiers.FragmentContainerId
import com.mumsapp.android.lobby.CreateLobbyCategoryFragment
import com.mumsapp.android.lobby.CreateLobbyTopicFragment
import com.mumsapp.android.lobby.LobbyRoomDetailsFragment
import com.mumsapp.android.lobby.LobbyFragment
import com.mumsapp.android.mums_app_offers.MumsAppOfferDetailsFragment
import com.mumsapp.android.mums_app_offers.MumsAppOffersFragment
import com.mumsapp.android.organisation.OrganisationDetailsFragment
import com.mumsapp.android.product.AddProductFragment
import com.mumsapp.android.product.MyProductsFragment
import com.mumsapp.android.product.MyWishlistFragment
import com.mumsapp.android.product.ProductFragment
import com.mumsapp.android.profile.MyProfileFragment
import com.mumsapp.android.profile.UserProfileFragment
import com.mumsapp.android.shop.ShopFilterFragment
import com.mumsapp.android.shop.ShopFragment
import com.mumsapp.domain.model.chat.TemplateChatThread
import com.mumsapp.domain.model.lobby.LobbyRoom
import javax.inject.Inject

class FragmentsNavigationService {

    private val fragmentManager: FragmentManager
    private val containerId: Int

    @Inject
    constructor(fragmentManager: FragmentManager, @FragmentContainerId containerId: Int) {
        this.fragmentManager = fragmentManager
        this.containerId = containerId
    }

    fun openAuthMenuFragment(addToBackStack: Boolean) {
        val fragment = createAuthMenuFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createAuthMenuFragment() = AuthMenuFragment.getInstance()

    fun openSignUpFragment(addToBackStack: Boolean) {
        val fragment = createSignUpFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createSignUpFragment() = SignUpFragment.getInstance()

    fun openSignInFragment(addToBackStack: Boolean) {
        val fragment = createSignInFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createSignInFragment() = SignInFragment.getInstance()

    fun openCreatePageFragment(addToBackStack: Boolean) {
        val fragment = createCreatePageFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createCreatePageFragment() = CreatePageFragment.getInstance()

    fun openForgetPasswordFragment(addToBackStack: Boolean) {

    }

    fun openMyProfileFragment(addToBackStack: Boolean) {
        val fragment = createMyProfileFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createMyProfileFragment() = MyProfileFragment.getInstance()

    fun openLobbyFragment(addToBackStack: Boolean) {
        val fragment = createLobbyFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createLobbyFragment() = LobbyFragment.getInstance()

    fun openChatFragment(addToBackStack: Boolean) {
        val fragment = createChatFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createChatFragment() = ChatFragment.getInstance()

    fun openFriendsChatFragment(addToBackStack: Boolean) {
        val fragment = createFriendsChatFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createFriendsChatFragment() = FriendsChatFragment.getInstance()

    fun openChatThreadFragment(thread: TemplateChatThread, addToBackStack: Boolean) {
        val fragment = createChatThreadFragment(thread)
        openFragment(fragment, addToBackStack)
    }

    fun createChatThreadFragment(thread: TemplateChatThread) = ChatThreadFragment.getInstance(thread)

    fun openShopFragment(addToBackStack: Boolean) {
        val fragment = createShopFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createShopFragment() = ShopFragment.getInstance()

    fun openShopFilterFragment(addToBackStack: Boolean) {
        val fragment = createShopFilterFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createShopFilterFragment() = ShopFilterFragment.getInstance()

    fun openProductFragment(productId: Int, addToBackStack: Boolean) {
        val fragment = createProductFragment(productId)
        openFragment(fragment, addToBackStack)
    }

    fun createProductFragment(productId: Int) = ProductFragment.getInstance(productId)

    fun openMyProductsFragment(addToBackStack: Boolean) {
        val fragment = createMyProductsFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createMyProductsFragment() = MyProductsFragment.getInstance()

    fun openMyWishlistFragment(addToBackStack: Boolean) {
        val fragment = createMyWishlistFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createMyWishlistFragment() = MyWishlistFragment.getInstance()

    fun openAddProductFragment(addToBackStack: Boolean) {
        val fragment = createAddProductFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createAddProductFragment() = AddProductFragment.getInstance()

    fun openCameraActivityForResults(output: Uri, requestCode: Int) {
        val intent = createCameraIntent(output)
        findTopFragment()!!.startActivityForResult(intent, requestCode)
    }

    fun createCameraIntent(output: Uri): Intent {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output)
        return intent
    }

    fun openGalleryActivityForResults(requestCode: Int) {
        val intent = createGalleryIntent()
        findTopFragment()!!.startActivityForResult(intent, requestCode)
    }

    fun createGalleryIntent(): Intent {
        return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }

    fun openOrganisationDetailsFragment(organisationId: String, addToBackStack: Boolean) {
        val fragment = createOrganisationDetailsFragment(organisationId)
        openFragment(fragment, addToBackStack)
    }

    fun createOrganisationDetailsFragment(organisationId: String) = OrganisationDetailsFragment.getInstance(organisationId)

    fun openMumsAppOffersFragment(addToBackStack: Boolean) {
        val fragment = createMumsAppOffersFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createMumsAppOffersFragment() = MumsAppOffersFragment.getInstance()

    fun openMumsAppOfferDetailsFragment(offerId: Int, addToBackStack: Boolean) {
        val fragment = createMumsAppOfferDetailsFragment(offerId)
        openFragment(fragment, addToBackStack)
    }

    fun createMumsAppOfferDetailsFragment(offerId: Int) = MumsAppOfferDetailsFragment.getInstance(offerId)

    fun openLobbyCategoryDetailsFragment(lobbyRoom: LobbyRoom, addToBackStack: Boolean) {
        val fragment = createLobbyCategoryDetailsFragment(lobbyRoom)
        openFragment(fragment, addToBackStack)
    }

    fun createLobbyCategoryDetailsFragment(lobbyRoom: LobbyRoom) = LobbyRoomDetailsFragment.getInstance(lobbyRoom)

    fun openCreateLobbyTopicFragment(lobbyCategoryId: Int, addToBackStack: Boolean) {
        val fragment = createCreateLobbyTopicFragment(lobbyCategoryId)
        openFragment(fragment, addToBackStack)
    }

    fun createCreateLobbyTopicFragment(lobbyCategoryId: Int) = CreateLobbyTopicFragment.getInstance(lobbyCategoryId)

    fun openUserProfileFragment(addToBackStack: Boolean) {
        val fragment = createUserProfileFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createUserProfileFragment() = UserProfileFragment.getInstance()

    fun openCreateLobbyCategoryFragment(addToBackStack: Boolean) {
        val fragment = createCreateLobbyCategoryFragment()
        openFragment(fragment, addToBackStack)
    }

    fun createCreateLobbyCategoryFragment() = CreateLobbyCategoryFragment.getInstance()

    fun popFragment() {
        fragmentManager.popBackStack()
    }

    fun popFragmentsToRoot() {
        if (getBackStackCount() > 0) {
            val entryName = fragmentManager.getBackStackEntryAt(0).name
            fragmentManager.popBackStackImmediate(entryName, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun getBackStackCount(): Int {
        return fragmentManager.backStackEntryCount
    }

    fun openMapFragment(fragment: SupportMapFragment, childFragmentManager: FragmentManager,
                        containerId: Int, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment, tag)
        transaction.commit()
        childFragmentManager.executePendingTransactions()
    }

    private fun openFragment(fragment: BaseFragment, addToBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.anim.fade_in, 0)

        val tag = fragment.javaClass.simpleName
        transaction.replace(containerId, fragment, tag)

        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
    }

    @Throws(UnsupportedOperationException::class)
    fun findTopFragment(): BaseFragment? {
        val f = fragmentManager.findFragmentById(containerId)
        return if (f != null) {
            f as? BaseFragment ?: throw UnsupportedOperationException("Invalid fragment in fragment manager: Every fragment on the stack should be a child of BaseFragment!")
        } else null

    }
}