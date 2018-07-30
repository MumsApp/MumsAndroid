package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import com.mumsapp.android.ui.views.BaseTextView

class PaginationWidget : CardView {

    @BindView(R.id.pagination_page_indicator)
    lateinit var pageIndicatorView: BaseTextView

    private var currentPage: Int = 1
    private var lastPage: Int = 1

    var firstPageListener: (() -> Unit)? = null
    var lastPageListener: (() -> Unit)? = null
    var nextPageListener: ((page: Int) -> Unit)? = null
    var previousPageListener: ((page: Int) -> Unit)? = null
    var pageChangeListener: ((page: Int) -> Unit)? = null

    constructor(context: Context) : super(context) {
        setup(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val view = View.inflate(context, R.layout.widget_pagination, this)
        ButterKnife.bind(view)
    }

    @OnClick(R.id.pagination_first_page)
    fun onFirstPageClick() {
        currentPage = 1
        firstPageListener?.invoke()
        pageChangeListener?.invoke(currentPage)

        updatePageIndicator()
    }

    @OnClick(R.id.pagination_previous_page)
    fun onPreviousPageClick() {
        if(currentPage > 1) {
            currentPage--
            previousPageListener?.invoke(currentPage)
            pageChangeListener?.invoke(currentPage)
        }

        updatePageIndicator()
    }

    @OnClick(R.id.pagination_next_page)
    fun onNextPageClick() {
        if(currentPage < lastPage) {
            currentPage++
            nextPageListener?.invoke(currentPage)
            pageChangeListener?.invoke(currentPage)
        }

        updatePageIndicator()
    }

    @OnClick(R.id.pagination_last_page)
    fun onLastPageClick() {
        currentPage = lastPage
        lastPageListener?.invoke()
        pageChangeListener?.invoke(lastPage)

        updatePageIndicator()
    }

    fun setLastPage(lastPage: Int) {
        this.lastPage = lastPage
        updatePageIndicator()
    }

    private fun updatePageIndicator() {
        val text = context.getString(R.string.pagination_widget_indication_pattern, currentPage, lastPage)
        pageIndicatorView.text = text
    }
}