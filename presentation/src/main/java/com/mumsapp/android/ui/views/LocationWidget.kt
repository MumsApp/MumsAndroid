package com.mumsapp.android.ui.views

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import com.mumsapp.android.MainApplication
import com.mumsapp.android.R
import javax.inject.Inject

class LocationWidget : CardView {

    @Inject
    lateinit var fragmentManager: FragmentManager

    lateinit var mapFragment: SupportMapFragment

    @BindView(R.id.location_widget_name)
    lateinit var locationNameView: BaseTextView

    @BindView(R.id.location_widget_switch)
    lateinit var switchView: BaseSwitch

    @BindView(R.id.location_widget_button)
    lateinit var buttonView: BaseTextView

    @BindView(R.id.location_widget_map_layout)
    lateinit var mapLayout: FrameLayout

    lateinit var currentMapLatitude: String
    lateinit var currentMapLongitude: String

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
        val view = View.inflate(context, R.layout.item_location_widget, this)
        MainApplication.getApplication(context).
        ButterKnife.bind(view)
        mapFragment = fragmentManager.findFragmentById(R.id.location_widget_map) as SupportMapFragment
        setupAttributes(context, attrs)
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?) {
        val array = context.obtainStyledAttributes(attrs, R.styleable.LocationWidget)

        val locationName = array.getString(R.styleable.LocationWidget_locationName)
        setLocationName(locationName)

        val switchVisible = array.getBoolean(R.styleable.LocationWidget_switchVisible, false)
        setSwitchVisibility(switchVisible)

        val switchValue = array.getBoolean(R.styleable.LocationWidget_switchDefaultValue, false)
        setSwitchDefaultValue(switchValue)

        val buttonVisible = array.getBoolean(R.styleable.LocationWidget_buttonVisible, false)
        setButtonVisibility(buttonVisible)

        val buttonText = array.getString(R.styleable.LocationWidget_buttonText)
        setButtonText(buttonText)

        val mapVisible = array.getBoolean(R.styleable.LocationWidget_mapVisible, false)
        setMapVisibility(mapVisible)

        val mapLatitude = array.getString(R.styleable.LocationWidget_mapLatitude)
        val mapLongitude = array.getString(R.styleable.LocationWidget_mapLongitude)
        setMapCoordinates(mapLatitude, mapLongitude)

        array.recycle()
    }

    fun setLocationName(name: String) {
        locationNameView.text = name
    }

    fun setSwitchVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, switchView)
    }

    fun setSwitchDefaultValue(value: Boolean) {
        switchView.isChecked = value
    }

    fun setButtonVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, buttonView)
    }

    fun setButtonText(text: String) {
        buttonView.text = text
    }

    fun setMapVisibility(visibility: Boolean) {
        setVisibilityFromBoolean(visibility, mapLayout)
        if(visibility) {
            configureMap()
        }
    }

    fun setMapCoordinates(latitude: String, longitude: String) {
        if(map != null) {
//            map.cameraPosition = CameraPosition()
        }
    }

    fun setMapCoordinates(latitude: Float, longitude: Float) {

    }

    private fun setVisibilityFromBoolean(value: Boolean, view: View) {
        val visibility = if (value) View.VISIBLE else View.INVISIBLE
        view.visibility = visibility
    }

    private fun configureMap() {
        mapFragment.getMapAsync(this::onMapReady)
    }

    private fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_maps_style))
    }
}