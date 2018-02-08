package com.mumsapp.data.repository

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.mumsapp.domain.repository.ResourceRepository
import javax.inject.Inject

class ResourceRepositoryImpl : ResourceRepository {

    private val context: Context

    @Inject
    constructor(context: Context) {
        this.context = context
    }

    override fun getString(@StringRes resId: Int): String {
        return this.context.getString(resId)
    }

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return this.context.getString(resId, *formatArgs)
    }

    override fun getString(name: String): String {
        val res = context.resources
        return res.getString(res.getIdentifier(name, "string", context.packageName))
    }

    override fun getStringArray(stringResId: Int): Array<String> {
        return this.context.resources.getStringArray(stringResId)
    }

    override fun getQuantityString(pluralResId: Int, count: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(pluralResId, count, *formatArgs)
    }

    override fun getQuantityString(pluralResId: Int, count: Int): String {
        return context.resources.getQuantityString(pluralResId, count)
    }

    override fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(this.context, colorRes)
    }

    override fun getInteger(@IntegerRes resId: Int): Int {
        return context.resources.getInteger(resId)
    }
}