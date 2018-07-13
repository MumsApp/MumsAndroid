package com.mumsapp.android.ui.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R
import kotlin.math.min

class NumberIncrementer : ConstraintLayout {

    private var minValue: Int = Int.MIN_VALUE
    private var maxValue: Int = Int.MAX_VALUE

    @BindView(R.id.number_incrementer_value)
    lateinit var valueView: BaseTextView

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
        val view = View.inflate(context, R.layout.view_number_incrementer, this) as ViewGroup
        ButterKnife.bind(this, view)

        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RangeSelector)

        val minValue = array.getInt(R.styleable.RangeSelector_minValue, 0)
        setMinValue(minValue)

        val maxValue = array.getInt(R.styleable.RangeSelector_maxValue, 99)
        setMaxValue(maxValue)

        setValue(0)

        array.recycle()
    }

    fun setMinValue(minValue: Int) {
        this.minValue = minValue
    }

    fun getMinValue() = minValue

    fun setMaxValue(maxValue: Int) {
        this.maxValue = maxValue
    }

    fun getMaxValue() = maxValue

    fun setValue(value: Int) {
        valueView.text = value.toString()
    }

    fun getValue() = valueView.text.toString().toInt()

    @OnClick(R.id.number_incrementer_minus)
    fun onMinusClick() {
        if(getValue() > minValue) {
            setValue(getValue() - 1)
        }
    }

    @OnClick(R.id.number_incrementer_plus)
    fun onPlusClick() {
        if(getValue() < maxValue) {
            setValue(getValue() + 1)
        }
    }
}