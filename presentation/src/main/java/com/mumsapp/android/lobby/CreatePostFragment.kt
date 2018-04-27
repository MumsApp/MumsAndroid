package com.mumsapp.android.lobby

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
import com.mumsapp.android.util.LOBBY_CATEGORY_ID_KEY
import javax.inject.Inject

class CreatePostFragment : BaseFragment(), CreatePostView {

    @Inject
    lateinit var presenter: CreatePostPresenter

    @BindView(R.id.create_post_top_bar)
    lateinit var topBar: TopBar

    override fun <T : LifecyclePresenter<LifecycleView>> getLifecyclePresenter() = presenter as T

    companion object {
        fun getInstance(lobbyCategoryId: Int): CreatePostFragment {
            val args = createArgumentsBundle(lobbyCategoryId)
            val fragment = CreatePostFragment()
            fragment.arguments = args

            return fragment
        }

        fun createArgumentsBundle(lobbyCategoryId: Int): Bundle {
            val args = Bundle()
            args.putInt(LOBBY_CATEGORY_ID_KEY, lobbyCategoryId)

            return args
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(ActivityComponent::class.java)?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_create_post, container, false)
        setUnbinder(ButterKnife.bind(this, v))
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passArgumentsToPresenter()
        presenter.attachViewWithLifecycle(this)
        topBar.setLeftButtonClickListener { presenter.onBackClick() }
        topBar.setRightTextClickListener { presenter.onDoneClick() }
    }

    private fun passArgumentsToPresenter() {
        val lobbyCategoryId = arguments!!.getInt(LOBBY_CATEGORY_ID_KEY)
    }
}