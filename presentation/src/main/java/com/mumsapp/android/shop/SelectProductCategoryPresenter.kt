package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.domain.interactor.shop.GetProductCategoriesUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.product.ProductCategoriesResponse
import javax.inject.Inject

class SelectProductCategoryPresenter : LifecyclePresenter<SelectProductCategoryView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                getProductCategoriesUseCase: GetProductCategoriesUseCase) : super() {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.getProductCategoriesUseCase = getProductCategoriesUseCase
    }

    override fun start() {
        loadCategories()
    }

    fun onBackClick() {
        fragmentsNavigationService.popFragment()
    }

    private fun loadCategories() {
        addDisposable(
                getProductCategoriesUseCase.execute(EmptyRequest())
                        .compose(applyOverlaysToObservable())
                        .subscribe(this::handleLoadCategoriesSuccess, this::handleApiError)
        )
    }

    private fun handleLoadCategoriesSuccess(categories: ProductCategoriesResponse) {
        
    }
}