package com.mumsapp.android.profile

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.ChildrenMapper
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.model.user.UserResponse
import com.mumsapp.domain.repository.ResourceRepository
import com.mumsapp.domain.utils.SessionManager
import javax.inject.Inject

class UserProfilePresenter : LifecyclePresenter<UserProfileView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val resourceRepository: ResourceRepository
    private val getUserProfileUseCase: GetUserProfileUseCase
    private val childrenMapper: ChildrenMapper
    private val sessionManager: SessionManager

    private var userId: Int = 0

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository,
                getUserProfileUseCase: GetUserProfileUseCase, childrenMapper: ChildrenMapper,
                sessionManager: SessionManager) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.getUserProfileUseCase = getUserProfileUseCase
        this.childrenMapper = childrenMapper
        this.sessionManager = sessionManager
    }

    fun setArguments(userId: Int) {
        this.userId = userId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onAddContactClick() {

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
        loadUserData()
    }

    private fun loadUserData() {
        val params = Params(userId, Params.LEVEL_FULL)

        addDisposable(
                getUserProfileUseCase.execute(params)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleUserLoadSuccess, this::handleApiError)
        )
    }

    private fun handleUserLoadSuccess(response: UserResponse) {
        val user = response.data
        showUserData(user)
    }

    private fun showUserData(user: UserResponse.User) {
        val name: String = resourceRepository.getString(R.string.first_and_last_name_pattern,
                user.firstName, user.lastName)

        view?.showProfileInfo(name, user.description)

        if (user.photo.src != null) {
            view?.loadAvatar(user.photo.src!!)
        }

        if (user.location.enabled == true) {
            user.location.let {
                view?.showLocation(it.latitude.toString(), it.longitude.toString(), it.name!!)
            }
        }

        val children = constructChildrenList(user)
        view?.showNumberOfKids(children)

        showCorrectButton(user)
    }

    private fun confirmRemoveContact() {

    }

    private fun cancelRemoveContact() {

    }

    private fun constructChildrenList(user: UserResponse.User): String {
        var children = StringBuilder("")

        user.children.forEachIndexed { index: Int, child: UserResponse.Child ->
            children.append(childrenMapper.getReadableName(child))

            if(index < user.children.size - 1) {
                children.append("\n")
            }
        }

        var childrenMessage = children.toString()

        if(childrenMessage.isBlank()) {
            childrenMessage = resourceRepository.getString(R.string.any_kids)
        }

        return childrenMessage
    }

    private fun showCorrectButton(user: UserResponse.User) {
        val currentUser = sessionManager.loadLoggedUser()!!.data

        if(userId == currentUser.id) {
            view?.setAddContactVisibility(false)
            view?.setRemoveContactVisibility(false)

            return
        }

        //TODO: Waiting for api
        view?.setAddContactVisibility(true)
        view?.setRemoveContactVisibility(false)
    }
}