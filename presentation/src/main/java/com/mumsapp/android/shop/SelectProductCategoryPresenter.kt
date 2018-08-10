package com.mumsapp.android.shop

import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.util.ProductCategoryMapper
import com.mumsapp.domain.interactor.shop.GetProductCategoriesUseCase
import com.mumsapp.domain.model.EmptyRequest
import com.mumsapp.domain.model.product.ProductCategoriesResponse
import javax.inject.Inject

class SelectProductCategoryPresenter : LifecyclePresenter<SelectProductCategoryView> {

    private val fragmentsNavigationService: FragmentsNavigationService
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase
    private val productCategoryMapper: ProductCategoryMapper

    @Inject
    constructor(fragmentsNavigationService: FragmentsNavigationService,
                getProductCategoriesUseCase: GetProductCategoriesUseCase,
                productCategoryMapper: ProductCategoryMapper) : super() {
        this.fragmentsNavigationService = fragmentsNavigationService
        this.getProductCategoriesUseCase = getProductCategoriesUseCase
        this.productCategoryMapper = productCategoryMapper
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
                        .map(productCategoryMapper::map)
                        .subscribe(this::handleLoadCategoriesSuccess, this::handleApiError)
        )
    }

    private fun handleLoadCategoriesSuccess(items: List<SelectProductCategoryItem>) {
        view?.showCategories(items, this::onCategoryClick)
    }

    private fun onCategoryClick(item: SelectProductCategoryItem) {

    }
}