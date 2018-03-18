package com.mumsapp.android.base

import android.support.v7.widget.RecyclerView
import com.mumsapp.domain.model.BaseResponse
import io.reactivex.subjects.PublishSubject
import java.util.*

abstract class BaseRecyclerViewAdapter<T : BaseResponse, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    var items: List<T> = ArrayList()

    protected var onItemClickListener: PublishSubject<T>? = PublishSubject.create()

    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.init(items[position])
        holder.itemView.setOnClickListener{
            onItemClickListener?.onNext(items[position])
        }
    }

    fun getItemsClickEmitter(): PublishSubject<T>? {
        return onItemClickListener
    }
}