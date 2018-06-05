package com.mumsapp.android.ui.widgets.mums_app_offers

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseSwitch
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer

class MumsAppOffersWidgetViewHolder : BaseViewHolder<TemplateMumsAppOffer> {

    @BindView(R.id.cell_mums_app_offers_label)
    lateinit var labelView: BaseTextView

    @BindView(R.id.cell_mums_app_offers_switch)
    lateinit var switchView: BaseSwitch

    constructor(itemView: View) : super(itemView) {
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: TemplateMumsAppOffer) {
        labelView.text = item.name
        switchView.isChecked = item.value
    }
}