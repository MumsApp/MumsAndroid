package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v7.widget.SwitchCompat
import android.util.AttributeSet

class BaseSwitch: SwitchCompat {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}