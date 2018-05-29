package com.mumsapp.android.chat

import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecycleView

interface ChatView : LifecycleView {

    fun setItems(fragments: List<BaseFragment>, titles: List<String>)
}