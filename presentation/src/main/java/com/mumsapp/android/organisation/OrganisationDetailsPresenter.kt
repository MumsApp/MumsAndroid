package com.mumsapp.android.organisation

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.product.ImageSliderItem
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.organisation.FeedResponse
import javax.inject.Inject

class OrganisationDetailsPresenter : LifecyclePresenter<OrganisationDetailsView> {

    private val fragmentsNavigationService: FragmentsNavigationService

    lateinit var organisationId: String

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService) {
        this.fragmentsNavigationService = fragmentsNavigationService
    }

    fun setArguments(organisationId: String) {
        this.organisationId = organisationId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    override fun start() {
        showMockedData()
    }

    private fun showMockedData() {
        view?.setDetails("St. Barnabas Primary School", "Description", null, true)
        showMockedPhotos()
        showMockedMembers()
        showMockedFeed()
    }

    private fun showMockedPhotos() {
        val photos = ArrayList<ImageSliderItem>()
        photos += ImageSliderItem(null, false)
        photos += ImageSliderItem(null, false)
        photos += ImageSliderItem(null, false)
        photos += ImageSliderItem(null, false)

        view?.showImageSlider(photos)
    }

    private fun showMockedMembers() {
        val members = ArrayList<TemplateChatRecipient>()
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")
        members += TemplateChatRecipient("1", "John Towar")

        view?.showMembers(members)
    }

    private fun showMockedFeed() {
        val feed = ArrayList<FeedResponse>()
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")
        feed += FeedResponse(null, "April 29, 2017", "John Towar", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec risus mi, tempor vel feugiat eget, suscipit sed metus. Aliquam dapibus imperdiet iaculis. Aliquam mollis accumsan leo ac luctus. Interdum et malesuada fames ac ante ipsum primis in faucibus.")

        view?.showFeed(feed)
    }
}