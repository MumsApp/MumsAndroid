package com.mumsapp.android.ui.widgets

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.CompoundButton
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.mumsapp.android.R
import com.mumsapp.android.common.features.HasComponent
import com.mumsapp.android.di.components.ActivityComponent
import com.mumsapp.android.navigation.FragmentsNavigationService
import com.mumsapp.android.ui.views.BaseSwitch
import com.mumsapp.android.ui.views.BaseTextView
import javax.inject.Inject

class LocationWidget : CardView {

    private val MAP_FRAGMENT_TAG = "mapFragment";

    @Inject
    lateinit var fragmentsNavigationService: FragmentsNavigationService

    lateinit var mapFragment: SupportMapFragment

    @BindView(R.id.location_widget_name)
    lateinit var locationNameView: BaseTextView

    @BindView(R.id.location_widget_switch)
    lateinit var switchView: BaseSwitch

    @BindView(R.id.location_widget_button)
    lateinit var buttonView: BaseTextView

    @BindView(R.id.location_widget_map_layout)
    lateinit var mapLayout: FrameLayout

    var currentMapLatitude: Double? = null
    var currentMapLongitude: Double? = null

    var map: GoogleMap? = null


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
        val view = View.inflate(context, R.layout.widget_location, this)
        if(context is HasComponent<*>) {
            (context as HasComponent<ActivityComponent>).getComponent().inject(this)
        }
        ButterKnife.bind(view)
        setupMapFragment()
        setupAttributes(context, attrs)
    }

    private fun setupMapFragment() {
        val childFragmentManager = fragmentsNavigationService.findTopFragment()!!.childFragmentManager
        var tmpMapFragment = childFragmentManager.findFragmentByTag(MAP_FRAGMENT_TAG) as SupportMapFragment?

        if(tmpMapFragment == null) {
            tmpMapFragment = SupportMapFragment()
            fragmentsNavigationService.openMapFragment(tmpMapFragment, childFragmentManager,
                    R.id.location_widget_map_layout, MAP_FRAGMENT_TAG)
        }
        mapFragment = tmpMapFragment
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.LocationWidget)

        val locationName: String? = array.getString(R.styleable.LocationWidget_locationName)
        setLocationName(locationName)

        val switchVisible = array.getBoolean(R.styleable.LocationWidget_switchVisible, false)
        setSwitchVisibility(switchVisible)

        val switchValue = array.getBoolean(R.styleable.LocationWidget_switchDefaultValue, false)
        setSwitchValue(switchValue)

        val buttonVisible = array.getBoolean(R.styleable.LocationWidget_buttonVisible, false)
        setButtonVisibility(buttonVisible)

        val buttonText = array.getString(R.styleable.LocationWidget_buttonText)
        setButtonText(buttonText)

        val mapVisible = array.getBoolean(R.styleable.LocationWidget_mapVisible, false)
        setMapVisibility(mapVisible)

        val mapLatitude: String? = array.getString(R.styleable.LocationWidget_mapLatitude)
        val mapLongitude: String? = array.getString(R.styleable.LocationWidget_mapLongitude)
        setMapCoordinates(mapLatitude, mapLongitude)

        array.recycle()
    }

    fun setLocationName(name: String?) {
        locationNameView.text = name
    }

    fun setSwitchVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, switchView)
    }

    fun setSwitchValue(value: Boolean) {
        switchView.isChecked = value
    }

    fun setButtonVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, buttonView)
    }

    fun setButtonText(text: String?) {
        buttonView.text = text
    }

    fun setMapVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, mapLayout)
        if(visibility) {
            configureMap()
        }
    }

    fun setMapCoordinates(latitude: String?, longitude: String?) {
       setMapCoordinates(latitude?.toDouble(), longitude?.toDouble())
    }

    fun setMapCoordinates(latitude: Double?, longitude: Double?) {
        currentMapLatitude = latitude
        currentMapLongitude = longitude

        safeMoveCamera()
    }

    private fun setVisibilityFromBoolean(value: Boolean, view: View) {
        val visibility = if (value) View.VISIBLE else View.GONE
        view.visibility = visibility
    }

    private fun configureMap() {
        mapFragment.getMapAsync(this::onMapReady)
    }

    private fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_maps_style))
        map?.uiSettings?.isScrollGesturesEnabled = false
        map?.uiSettings?.isZoomControlsEnabled = false

        safeMoveCamera()
    }

    private fun safeMoveCamera() {
        if(currentMapLatitude != null && currentMapLongitude != null && map != null) {
            val position = LatLng(currentMapLatitude!!, currentMapLongitude!!)
            val update = CameraUpdateFactory.newLatLngZoom(position, 15f)
            map?.moveCamera(update)

            val marker = MarkerOptions()
            marker.position(LatLng(currentMapLatitude!!, currentMapLongitude!!))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker))
            map?.addMarker(marker)
        }
    }

    fun setWidgetButtonListener(lambda: ((v: View) -> Unit)) {
        buttonView.setOnClickListener(lambda)
    }

    fun setSwitchChangeListener(lambda: (buttonView: CompoundButton, isChecked: Boolean) -> Unit) {
        switchView.setOnCheckedChangeListener(lambda)
    }
}