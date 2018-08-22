package com.mumsapp.android.ui.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.PluralsRes
import android.support.annotation.StringRes
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.innovattic.rangeseekbar.RangeSeekBar
import com.mumsapp.android.R
import com.mumsapp.android.ui.widgets.LocationWidget

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

    private var preffix: String = ""
    private var suffix: String = ""
    private var suffixPluralId: Int? = null

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
        seekBar.seekBarChangeListener = object : RangeSeekBar.SeekBarChangeListener {

            override fun onStartedSeeking() {
            }

            override fun onStoppedSeeking() {
            }

            override fun onValueChanged(minThumbValue: Int, maxThumbValue: Int) {
                updateLeftValue(minThumbValue)
                updateRightValue(maxThumbValue)
            }
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

    fun getBottomLabel() = secondLabelView.text

    fun getBottomLabelVisibility() = secondLabelView.visibility

    fun setMinValue(value: Float) {
        seekBar.minRange = value.toInt()
    }

    fun setMaxValue(value: Float) {
        seekBar.max = value.toInt()
    }

    fun setSelectedMinValue(minValue: Int) {
        seekBar.setMinThumbValue(minValue)
        updateLeftValue(minValue)
    }

    fun getSelectedMinValue(): Int {
        return seekBar.getMinThumbValue()
    }

    fun setSelectedMaxValue(maxValue: Int) {
        seekBar.setMaxThumbValue(maxValue)
        updateRightValue(maxValue)
    }

    fun getSelectedMaxValue(): Int {
        return seekBar.getMaxThumbValue()
    }

    fun setSelectionEnabled(enabled: Boolean) {
        seekBar.isEnabled = enabled
    }

    fun setPrefixValue(@StringRes value: Int) {
         preffix = context.getString(value) + " "
    }

    fun setSuffixValue(@StringRes value: Int) {
        suffix = " " + context.getString(value)
    }

    fun setSuffixPlural(@PluralsRes value: Int) {
        suffixPluralId = value
    }

    private fun updateLeftValue(value: Int) {
        val text = formatValue(value)

        leftValueView.text = text
    }

    private fun updateRightValue(value: Int) {
        val text = formatValue(value)

        rightValueView.text = text
    }

    private fun formatValue(value: Int): String {
        var plural = ""
        if(suffixPluralId != null) {
            plural = " " + context.resources.getQuantityString(suffixPluralId!!, value)
        }
        return "$preffix$value$suffix$plural"
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val state = State(superState)
        state.leftValue = getSelectedMinValue()
        state.rightValue = getSelectedMaxValue()
        state.preffix = preffix
        state.suffix = suffix

        if(suffixPluralId != null) {
            state.suffixPluralId = suffixPluralId!!
        }

        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state is State) {
            super.onRestoreInstanceState(state.superState)

            setSelectedMinValue(state.leftValue)
            setSelectedMaxValue(state.rightValue)
            preffix = state.preffix
            suffix = state.suffix

            if(state.suffixPluralId != 0) {
                suffixPluralId = state.suffixPluralId
            }

            return
        }

        super.onRestoreInstanceState(state)
    }

    class State : BaseSavedState {

        var leftValue = 0
        var rightValue = 0
        var preffix: String = ""
        var suffix: String = ""
        var suffixPluralId: Int = 0


        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel) : super(source) {
            val intArray = IntArray(3)
            source.readIntArray(intArray)
            leftValue = intArray[0]
            rightValue = intArray[1]
            suffixPluralId = intArray[2]

            val stringArray = Array(2) {""}
            source.readStringArray(stringArray)
            preffix = stringArray[0]
            suffix = stringArray[1]
        }
        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            super.writeToParcel(dest, flags)

            val intArray = IntArray(3)
            intArray[0] = leftValue
            intArray[1] = rightValue
            intArray[2] = suffixPluralId

            writeIntArray(intArray)

            val stringArray = Array(2){
                when(it) {
                    0 -> preffix
                    1 -> suffix
                    else -> ""
                }
            }

            writeStringArray(stringArray)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
                override fun createFromParcel(source: Parcel): State = State(source)
                override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
            }
        }
    }
}