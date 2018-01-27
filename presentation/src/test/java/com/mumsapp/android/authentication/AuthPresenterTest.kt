package com.mumsapp.android.authentication

import android.arch.lifecycle.Lifecycle
import com.mumsapp.android.navigation.ActivitiesNavigationService
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.winterbe.expekt.should
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class AuthPresenterTest: Spek({

    lateinit var mockedView: AuthView
    lateinit var mockedLifecycle: Lifecycle
    lateinit var mockedFragmentsNavigationService: FragmentsNavigationService
    lateinit var mockedActivitiesNavigationService: ActivitiesNavigationService
    lateinit var mockedFragment: AuthMenuFragment
    lateinit var presenter: AuthPresenter

    beforeEachTest {
        mockedView = mockk(relaxed = true)
        mockedLifecycle = mockk(relaxed = true)
        mockedFragmentsNavigationService = mockk(relaxed = true)
        mockedActivitiesNavigationService = mockk(relaxed = true)
        mockedFragment = mockk(relaxed = true)

        every { mockedView.lifecycle } returns mockedLifecycle

        presenter = AuthPresenter(mockedFragmentsNavigationService, mockedActivitiesNavigationService)
        presenter.attachViewWithLifecycle(mockedView)
    }

    describe("create") {
        beforeEachTest {
            presenter.onCreate()
        }

        it("should open auth menu fragment") {
            verify { mockedFragmentsNavigationService.openAuthMenuFragment(true) }
        }
    }

    describe("handleBackOrDelegateToSystem") {

        var returnValue: Boolean? = null

        context("back stack count equal to 1") {

            beforeEachTest {
                every { mockedFragmentsNavigationService.getBackStackCount() } returns 1
                returnValue = presenter.handleBackOrDelegateToSystem()
            }

            it ("should finish activity") {
                verify { mockedActivitiesNavigationService.finishCurrentActivity() }
            }

            it("should return true") {
                returnValue.should.equal(false)
            }
        }

        context("back stack count more than 1") {
            beforeEachTest {
                every { mockedFragmentsNavigationService.getBackStackCount() } returns 2
            }

            context("null top fragment") {
                beforeEachTest {
                    every { mockedFragmentsNavigationService.findTopFragment() } returns null
                    returnValue = presenter.handleBackOrDelegateToSystem()
                }

                it("should return true") {
                    returnValue.should.equal(true)
                }
            }

            context("not null top fragment") {

                beforeEachTest {
                    every { mockedFragmentsNavigationService.findTopFragment() } returns mockedFragment
                }

                context("goingBack returns true") {
                    beforeEachTest {
                        every { mockedFragment.goingBack() } returns true
                        returnValue = presenter.handleBackOrDelegateToSystem()
                    }

                    it("should pop fragment") {
                        verify { mockedFragmentsNavigationService.popFragment() }
                    }

                    it("should return false") {
                        returnValue.should.equal(false)
                    }
                }

                context("goingBack returns false") {
                    beforeEachTest {
                        every { mockedFragment.goingBack() } returns false
                        returnValue = presenter.handleBackOrDelegateToSystem()
                    }

                    it("should return true") {
                        returnValue.should.equal(true)
                    }
                }
            }
        }
    }
})