package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.mumsapp.android.R

class CardSwitch : CardView {

    lateinit var switch: BaseSwitch

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
        val view = View.inflate(context, R.layout.view_card_switch, this) as ViewGroup
        switch = view.getChildAt(0) as BaseSwitch
        switch.id = View.generateViewId()
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val textArray = context.obtainStyledAttributes(attrs, R.styleable.TextValues)

        val text = textArray.getString(R.styleable.TextValues_android_text)
        setText(text)

        textArray.recycle()

        val switchArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchValues)

        val checked = switchArray.getBoolean(R.styleable.SwitchValues_android_checked, false)
        setChecked(checked)

        switchArray.recycle()
    }

    fun setText(text: String?) {
        switch.text = text
    }

    fun setChecked(checked: Boolean) {
        switch.isChecked = checked
    }

    fun setOnCheckedChangeListener(listener: CompoundButton.OnCheckedChangeListener?) {
        switch.setOnCheckedChangeListener(listener)
    }
}