package com.mumsapp.android.ui.widgets.mums_app_offers

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseRecyclerViewAdapter
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer
import javax.inject.Inject

class MumsAppOffersWidgetAdapter : BaseRecyclerViewAdapter<TemplateMumsAppOffer, MumsAppOffersWidgetViewHolder> {

    @Inject
    constructor()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MumsAppOffersWidgetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_mums_app_offers_widget, parent, false)
        return MumsAppOffersWidgetViewHolder(itemView)
    }
}