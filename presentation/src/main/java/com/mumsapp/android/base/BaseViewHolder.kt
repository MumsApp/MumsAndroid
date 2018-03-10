package com.mumsapp.android.base

import android.support.v7.widget.RecyclerView
import android.view.View
import com.mumsapp.domain.model.BaseResponse

abstract class BaseViewHolder<in T : BaseResponse>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun init(item: T)
}