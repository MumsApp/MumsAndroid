package com.mumsapp.android.profile

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class UserProfilePresenter : LifecyclePresenter<UserProfileView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository

    private var userId: Int = 0

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService, resourceRepository: ResourceRepository) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
    }

    fun setArguments(userId: Int) {
        this.userId = userId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onRemoveContactClick() {
        val title = resourceRepository.getString(R.string.are_you_sure_you_want_to_remove_from_your_contacts_question_mark)
        val description = resourceRepository.getString(R.string.chats_with_this_user_will_be_lost)
        val confirmationButton = resourceRepository.getString(R.string.yes_coma_remove_this_contact)
        val cancelButton = resourceRepository.getString(R.string.no_coma_get_me_out_of_here)
        
        view?.showRemoveUserDialog(null, "John Conor", title, description,
                confirmationButton, cancelButton, this::confirmRemoveContact, this::cancelRemoveContact)
    }

    override fun start() {
        showMockedData()
    }

    private fun showMockedData() {
        view?.showProfileInfo("Template name", "Description")
        view?.showNumberOfKids("Mother of one 1 year old.")
    }

    private fun confirmRemoveContact() {

    }

    private fun cancelRemoveContact() {

    }
}