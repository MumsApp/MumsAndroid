package com.mumsapp.android.ui.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R

class RangeSelector : ConstraintLayout {

    @BindView(R.id.range_selector_label)
    lateinit var labelView: BaseTextView

    @BindView(R.id.range_selector_second_label)
    lateinit var secondLabelView: BaseTextView

    @BindView(R.id.range_selector_left_value)
    lateinit var leftValueView: BaseTextView

    @BindView(R.id.range_selector_right_value)
    lateinit var rightValueView: BaseTextView

    @BindView(R.id.range_selector_seek_bar)
    lateinit var seekBar: RangeSeekBar

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
        val view = View.inflate(context, R.layout.item_range_selector, this)
        ButterKnife.bind(view)
        configureViews()
        setupAttributes(context, attrs)
    }

    private fun configureViews() {
        seekBar.setOnRangeSeekbarChangeListener { minValue, maxValue ->
            leftValueView.text = minValue.toString()
            rightValueView.text = maxValue.toString()
        }
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.RangeSelector)

        val label = array.getString(R.styleable.RangeSelector_label)
        setLabel(label)

        val minValue = array.getFloat(R.styleable.RangeSelector_minValue, 0f)
        setMinValue(minValue)

        val maxValue = array.getFloat(R.styleable.RangeSelector_maxValue, 10f)
        setMaxValue(maxValue)

        array.recycle()
    }

    fun setLabel(label: String) {
        labelView.text = label
    }

    fun showBottomLabel(label: String?) {
        secondLabelView.text = label
        secondLabelView.visibility = View.VISIBLE
    }

    fun hideBottomLabel() {
        secondLabelView.visibility = View.GONE
    }

    fun setMinValue(value: Float) {
        seekBar.setMinValue(value)
    }

    fun setMaxValue(value: Float) {
        seekBar.setMaxValue(value)
    }

    fun setSelectedMinValue(minValue: Int) {
        seekBar.left = minValue
        leftValueView.text = minValue.toString()
    }

    fun getSelectedMinValue() = seekBar.left

    fun setSelectedMaxValue(maxValue: Int) {
        seekBar.right = maxValue
        rightValueView.text = maxValue.toString()
    }

    fun getSelectedMaxValue() = seekBar.right

    fun setSelectionEnabld(enabled: Boolean) {
        seekBar.isEnabled = enabled
    }
}