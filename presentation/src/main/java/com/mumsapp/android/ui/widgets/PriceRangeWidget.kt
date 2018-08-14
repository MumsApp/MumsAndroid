package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.ui.views.BaseSwitch
import com.mumsapp.android.ui.views.RangeSelector

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
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RangeSelector)

        val minValue = array.getFloat(R.styleable.RangeSelector_minValue, 0f)
        rangeSelector.setMinValue(minValue)
        rangeSelector.setSelectedMinValue(minValue.toInt())

        val maxValue = array.getFloat(R.styleable.RangeSelector_maxValue, 10f)
        rangeSelector.setMaxValue(maxValue)
        rangeSelector.setSelectedMaxValue(maxValue.toInt())

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

    fun getSwitchValue() = freeSwitch.isChecked

    fun setSwitchValue(value: Boolean) {
        freeSwitch.isChecked = value
    }
    fun setSwitchChangeListener(listener: (value: Boolean) -> Unit) {
        freeSwitch.setOnCheckedChangeListener { _: CompoundButton, value: Boolean ->
            listener.invoke(value)
        }
    }

    fun setSelectionEnabled(enabled: Boolean) {
        rangeSelector.setSelectionEnabld(enabled)
    }
}