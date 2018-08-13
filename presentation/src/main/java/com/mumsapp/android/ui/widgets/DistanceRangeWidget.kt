package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.ui.views.BaseButton
import com.mumsapp.android.ui.views.RangeSelector

class DistanceRangeWidget : CardView {

    @BindView(R.id.distance_range_selector)
    lateinit var rangeSelector: RangeSelector

    @BindView(R.id.distance_range_set_location)
    lateinit var setLocationButton: BaseButton

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
        val view = View.inflate(context, R.layout.widget_distance_range, this)
        ButterKnife.bind(view)
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RangeSelector)

        val minValue = array.getFloat(R.styleable.RangeSelector_minValue, 0f)
        rangeSelector.setMinValue(minValue)

        val maxValue = array.getFloat(R.styleable.RangeSelector_maxValue, 10f)
        rangeSelector.setMaxValue(maxValue)

        array.recycle()
    }

    fun getSelectedMin() = rangeSelector.getSelectedMinValue()

    fun setSelectedMin(value: Int) {
        rangeSelector.setSelectedMinValue(value)
    }

    fun getSelectedMax() = rangeSelector.getSelectedMaxValue()

    fun setSelectedMax(value: Int) {
        rangeSelector.setSelectedMaxValue(value)
    }

    fun setSelectionEnabled(enabled: Boolean) {
        rangeSelector.setSelectionEnabld(enabled)
    }

    fun setOnSetLocationClickListener(listener: () -> Unit) {
        setLocationButton.setOnClickListener{listener.invoke()}
    }

    fun setSecondLineText(text: String?) {
        rangeSelector.showBottomLabel(text)
    }
}