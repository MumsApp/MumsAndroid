package com.mumsapp.android.authentication

import android.arch.lifecycle.Lifecycle
import com.mumsapp.android.navigation.FragmentsNavigationService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class AuthPresenterTest: Spek({

    lateinit var mockedView: AuthView
    lateinit var mockedLifecycle: Lifecycle
    lateinit var mockedFragmentsNavigationService: FragmentsNavigationService
    lateinit var presenter: AuthPresenter

    beforeEachTest {
        mockedView = mockk(relaxed = true)
        mockedLifecycle = mockk(relaxed = true)
        mockedFragmentsNavigationService = mockk(relaxed = true)

        every { mockedView.lifecycle } returns mockedLifecycle

        presenter = AuthPresenter(mockedFragmentsNavigationService)
        presenter.attachViewWithLifecycle(mockedView)
    }

    describe("start") {
        beforeEachTest {
            presenter.onStart()
        }

        it("should open auth menu fragment") {
            verify { mockedFragmentsNavigationService.openAuthMenuFragment() }
        }
    }
})