package com.mumsapp.android.organisation

import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseViewHolder
import com.mumsapp.android.ui.views.BaseTextView
import com.mumsapp.android.util.ImagesLoader
import com.mumsapp.domain.model.organisation.FeedResponse
import de.hdodenhof.circleimageview.CircleImageView

class OrganisationFeedViewHolder : BaseViewHolder<FeedResponse> {

    private val imagesLoader: ImagesLoader

    @BindView(R.id.organisation_details_feed_cell_avatar)
    lateinit var imageView: CircleImageView

    @BindView(R.id.organisation_details_feed_cell_date)
    lateinit var dateView: BaseTextView

    @BindView(R.id.organisation_details_feed_cell_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.organisation_details_feed_cell_content)
    lateinit var contentView: BaseTextView

    constructor(imagesLoader: ImagesLoader, itemView: View) : super(itemView) {
        this.imagesLoader = imagesLoader
        ButterKnife.bind(this, itemView)
    }

    override fun init(item: FeedResponse) {
        dateView.text = item.date
        nameView.text = item.name
        contentView.text = item.content
    }
}