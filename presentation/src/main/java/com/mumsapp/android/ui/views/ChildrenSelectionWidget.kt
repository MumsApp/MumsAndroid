package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.mumsapp.android.R

class ChildrenSelectionWidget : CardView {

    @BindView(R.id.children_selection_widget_children_count)
    lateinit var childrenCountView: TextView

    @BindView(R.id.children_selection_seek_bar)
    lateinit var seekBar: RangeSeekBar

    @BindView(R.id.children_selection_range_min)
    lateinit var selectionRangeMinView: TextView

    @BindView(R.id.children_selection_range_max)
    lateinit var selectionRangeMaxView: TextView

    private var childrenCount = 0

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
        val view = View.inflate(context, R.layout.widget_children_selection, this)
        ButterKnife.bind(view)
        configureViews()
    }

    private fun configureViews() {
        setChildrenCount(0)
        seekBar.setOnRangeSeekbarChangeListener({ minValue, maxValue ->
            selectionRangeMinView.text = minValue.toString()
            selectionRangeMaxView.text = maxValue.toString()
        })
    }

    @OnClick(R.id.children_selection_widget_plus)
    fun onPlusClick() {
        childrenCount++
        setChildrenCount(childrenCount)
    }

    @OnClick(R.id.children_selection_widget_minus)
    fun onMinusClick() {
        if(childrenCount > 0) {
            childrenCount--
            setChildrenCount(childrenCount)
        }
    }

    fun setChildrenCount(childrenCount: Int) {
        this.childrenCount = childrenCount
        childrenCountView.text = childrenCount.toString()
    }

    fun getChildrenCount() = childrenCount

    fun setRange(min: Int, max: Int) {
        seekBar.left = min
        seekBar.right = max
    }

    fun getStartRange() = seekBar.left

    fun getEndRange() = seekBar.right
}