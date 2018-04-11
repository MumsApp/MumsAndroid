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
import com.mumsapp.android.ui.views.TopBar
import com.mumsapp.android.util.ORGANISATION_ID_KEY
import javax.inject.Inject

class OrganisationDetailsFragment : BaseFragment(), OrganisationDetailsView {

    @Inject
    lateinit var presenter: OrganisationDetailsPresenter

    @BindView(R.id.organisation_details_top_bar)
    lateinit var topBar: TopBar

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
}