package com.mumsapp.android.common.adapters

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.mumsapp.android.base.BaseFragment

class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    var fragments: List<BaseFragment> = ArrayList()
    var titles: List<String> = ArrayList()

    fun setItems(fragments: List<BaseFragment>, titles: List<String>) {
        this.fragments = fragments
        this.titles = titles
        notifyDataSetChanged()
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]
}