package com.mumsapp.android.ui.widgets.mums_app_offers

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.mums_app_offers.TemplateMumsAppOffer
import javax.inject.Inject

class MumsAppOffersWidget : CardView {

    @BindView(R.id.mums_app_offers_widget_recycler_view)
    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var adapter: MumsAppOffersWidgetAdapter

    constructor(context: Context) : super(context) {
        setup(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        val view = View.inflate(context, R.layout.widget_mums_app_offers, this)
        ButterKnife.bind(view)

        if(context is HasComponent<*>) {
            (context as HasComponent<ActivityComponent>).getComponent().inject(this)
        }
    }

    fun setOffers(offers: List<TemplateMumsAppOffer>) {
        adapter.items = offers
        recyclerView.adapter = adapter
    }
}