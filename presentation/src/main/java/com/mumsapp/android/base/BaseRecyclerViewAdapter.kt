package com.mumsapp.android.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.domain.model.BaseResponse
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

abstract class BaseRecyclerViewAdapter<T : BaseResponse, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    var items: List<T> = ArrayList()

    private var onItemClickListener: ((item: T) -> Unit)? = null
    private var onItemLongClickListener: ((item: T) -> Unit)? = null

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.init(items[position])
        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(items[position])
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.invoke(items[position])
            onItemLongClickListener != null
        }
    }

    fun setItemsClickListener(listener: (item: T) -> Unit) {
        onItemClickListener = listener
    }

    fun setItemsLongClickListener(listener: (item: T) -> Unit) {
        onItemLongClickListener = listener
    }

    protected fun inflate(parent: ViewGroup, layoutResId: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }
}