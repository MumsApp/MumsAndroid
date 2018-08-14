package com.mumsapp.android.profile

import com.mumsapp.android.R
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.ChildrenMapper
import com.mumsapp.domain.interactor.user.AddUserFriendUseCase
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase
import com.mumsapp.domain.interactor.user.GetUserProfileUseCase.Params
import com.mumsapp.domain.interactor.user.RemoveUserFriendUseCase
import com.mumsapp.domain.interactor.user.UserFriendRequest
import com.mumsapp.domain.model.EmptyResponse
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
    private val addUserFriendUseCase: AddUserFriendUseCase
    private val removeUserFriendUseCase: RemoveUserFriendUseCase

    private var userId: Int = 0
    private lateinit var loadedUser: UserResponse.User

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                resourceRepository: ResourceRepository,
                getUserProfileUseCase: GetUserProfileUseCase, childrenMapper: ChildrenMapper,
                sessionManager: SessionManager,
                addUserFriendUseCase: AddUserFriendUseCase,
                removeUserFriendUseCase: RemoveUserFriendUseCase) {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.resourceRepository = resourceRepository
        this.getUserProfileUseCase = getUserProfileUseCase
        this.childrenMapper = childrenMapper
        this.sessionManager = sessionManager
        this.addUserFriendUseCase = addUserFriendUseCase
        this.removeUserFriendUseCase = removeUserFriendUseCase
    }

    fun setArguments(userId: Int) {
        this.userId = userId
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    fun onAddContactClick() {
        addContact(userId)
    }

    fun onRemoveContactClick() {
        val userName = resourceRepository.getString(R.string.first_and_last_name_pattern, loadedUser.firstName, loadedUser.lastName)
        val title = resourceRepository.getString(R.string.are_you_sure_you_want_to_remove_from_your_contacts_question_mark)
        val description = resourceRepository.getString(R.string.chats_with_this_user_will_be_lost)
        val confirmationButton = resourceRepository.getString(R.string.yes_coma_remove_this_contact)
        val cancelButton = resourceRepository.getString(R.string.no_coma_get_me_out_of_here)

        view?.showRemoveUserDialog(loadedUser.photo.src, userName, title, description,
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
        this.loadedUser = user
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

        if(user.isFriend) {
            view?.setAddContactVisibility(false)
            view?.setRemoveContactVisibility(true)
        } else {
            view?.setAddContactVisibility(true)
            view?.setRemoveContactVisibility(false)
        }
    }

    private fun confirmRemoveContact() {
        removeContact(userId)
    }

    private fun cancelRemoveContact() {

    }

    private fun addContact(id: Int) {
        val request = UserFriendRequest(id)
        addDisposable(
                addUserFriendUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleAddingContactSuccess, this::handleApiError)
        )
    }

    private fun handleAddingContactSuccess(response: EmptyResponse) {
        view?.setAddContactVisibility(false)
        view?.setRemoveContactVisibility(true)
    }

    private fun removeContact(id: Int) {
        val request = UserFriendRequest(id)

        addDisposable(
                removeUserFriendUseCase.execute(request)
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleRemovingContactSuccess, this::handleApiError)
        )
    }

    private fun handleRemovingContactSuccess(response: EmptyResponse) {
        view?.setAddContactVisibility(true)
        view?.setRemoveContactVisibility(false)
    }
}