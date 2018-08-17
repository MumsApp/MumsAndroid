package com.mumsapp.android.organisation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.mumsapp.android.R
import com.mumsapp.android.base.BaseFragment
import com.mumsapp.android.base.LifecyclePresenter
import com.mumsapp.android.base.LifecycleView
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.product.ImageSliderItem
import com.mumsapp.android.ui.views.*
import com.mumsapp.android.ui.widgets.LocationWidget
import com.mumsapp.android.ui.widgets.members.MembersWidget
import com.mumsapp.domain.utils.ORGANISATION_ID_KEY
import com.mumsapp.domain.model.chat.TemplateChatRecipient
import com.mumsapp.domain.model.organisation.FeedResponse
import javax.inject.Inject

class OrganisationDetailsFragment : BaseFragment(), OrganisationDetailsView {

    @Inject
    lateinit var presenter: OrganisationDetailsPresenter

    @Inject
    lateinit var photosAdapter: OrganisationPhotosAdapter

    @Inject
    lateinit var feedAdapter: OrganisationFeedAdapter

    @BindView(R.id.organisation_details_top_bar)
    lateinit var topBar: TopBar

    @BindView(R.id.organisation_details_avatar)
    lateinit var avatarView: CircleImageView

    @BindView(R.id.organisation_details_verification_status)
    lateinit var verificationStatusView: BaseImageView

    @BindView(R.id.organisation_details_name)
    lateinit var nameView: BaseTextView

    @BindView(R.id.organisation_details_description)
    lateinit var descriptionView: BaseTextView

    @BindView(R.id.organisation_details_location_widget)
    lateinit var locationWidget: LocationWidget

    @BindView(R.id.organisation_details_photos_recycler)
    lateinit var imagesRecycler: HorizontalRecyclerView

    @BindView(R.id.organisation_details_members)
    lateinit var membersWidget: MembersWidget

    @BindView(R.id.organisation_details_feed_recycler)
    lateinit var feedRecycler: CardsRecyclerView

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(organisationId: String) : OrganisationDetailsFragment {
            val args = createArgumentBundle(organisationId)

            val fragment = OrganisationDetailsFragment()
            fragment.arguments = args

            return fragment
        }

        private fun createArgumentBundle(organisationId: String) : Bundle {
            val args = Bundle()
            args.putSerializable(ORGANISATION_ID_KEY, organisationId)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_organisation_details, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
    }

    private fun passArgumentsToPresenter() {
        val organisationID = arguments!!.getString(ORGANISATION_ID_KEY)
        presenter.setArguments(organisationID)
    }

    override fun setDetails(name: String, description: String?, avatarUrl: String?, isVerified: Boolean) {
        nameView.text = name
        descriptionView.text = description
        verificationStatusView.visibility = if(isVerified) View.VISIBLE else View.GONE
    }

    override fun showImageSlider(items: List<ImageSliderItem>) {
        photosAdapter.items = items

        if(imagesRecycler.adapter == null) {
            imagesRecycler.adapter = photosAdapter
        }
    }

    override fun showMembers(users: List<TemplateChatRecipient>) {
        membersWidget.setMembers(users)
    }

    override fun showFeed(feed: List<FeedResponse>) {
        feedAdapter.items = feed

        if(feedRecycler.adapter == null) {
            feedRecycler.adapter = feedAdapter
        }
    }
}