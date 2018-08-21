package com.mumsapp.android.ui.views

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import com.rafakob.floatingedittext.FloatingEditText

class BaseInput : FloatingEditText {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun clearError() {
        error = null
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val state = State(superState)
        state.text = text

        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state is State) {
            super.onRestoreInstanceState(state.superState)

            text = state.text

            return
        }

        super.onRestoreInstanceState(state)
    }

    class State : BaseSavedState {

        var text: String? = null

        constructor(superState: Parcelable) : super(superState)

        constructor(source: Parcel) : super(source) {
            text = source.readString()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
            super.writeToParcel(this, flags)
            writeString(text)
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