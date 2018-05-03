package com.mumsapp.android.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.view.MotionEvent



class BaseEditText : EditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    
    fun enableScroll() {
        setOnTouchListener { v, event ->
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
    }
}