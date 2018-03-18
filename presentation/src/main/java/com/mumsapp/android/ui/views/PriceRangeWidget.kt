package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R

class PriceRangeWidget : CardView {

    @BindView(R.id.price_range_free_switch)
    lateinit var freeSwitch: BaseSwitch

    @BindView(R.id.price_range_selector)
    lateinit var rangeSelector: RangeSelector

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
        val view = View.inflate(context, R.layout.widget_price_range, this)
        ButterKnife.bind(view)
    }

    fun getSelectedMin() = rangeSelector.getMinValue()

    fun getSelectedMax() = rangeSelector.getMaxValue()

    fun getSwitchValue() = freeSwitch.isChecked
}