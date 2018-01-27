package com.mumsapp.android.authentication

import android.arch.lifecycle.Lifecycle
import com.mumsapp.android.navigation.FragmentsNavigationService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class AuthMenuPresenterTest: Spek({

    lateinit var mockedView: AuthMenuView
    lateinit var mockedLifecycle: Lifecycle
    lateinit var mockedFragmentsNavigationService: FragmentsNavigationService
    lateinit var presenter: AuthMenuPresenter

    beforeEachTest {
        mockedView = mockk(relaxed = true)
        mockedLifecycle = mockk(relaxed = true)
        mockedFragmentsNavigationService = mockk(relaxed = true)

        every { mockedView.lifecycle } returns mockedLifecycle

        presenter = AuthMenuPresenter(mockedFragmentsNavigationService)
        presenter.attachViewWithLifecycle(mockedView)
    }

    describe("onSignUpClick") {
        beforeEachTest {
            presenter.onSignUpClick()
        }

        it("should open sign up fragment") {
            verify { mockedFragmentsNavigationService.openSignUpFragment(true) }
        }
    }

    describe("onSignInClick") {
        beforeEachTest {
            presenter.onSignInClick()
        }

        it("should open sign up fragment") {
            verify { mockedFragmentsNavigationService.openSignInFragment(true) }
        }
    }

    describe("onCreatePageClick") {
        beforeEachTest {
            presenter.onCreatePageClick()
        }

        it("should open sign up fragment") {
            verify { mockedFragmentsNavigationService.openCreatePageFragment(true) }
        }
    }
})