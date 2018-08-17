package com.mumsapp.data.utils

import android.location.Location
import com.mumsapp.domain.utils.LocationHelper
import com.mumsapp.domain.utils.MILES_IN_METER
import javax.inject.Inject

class LocationHelperImpl : LocationHelper {

    @Inject
    constructor()

    override fun calculateDistanceInMiles(firstPlaceLat: Double, firstPlaceLon: Double,
                                          secondPlaceLat: Double, secondPlaceLon: Double): Double {
        val firstLocation = Location("")
        firstLocation.latitude = firstPlaceLat
        firstLocation.longitude = firstPlaceLon

        val secondLocation = Location("")
        secondLocation.latitude = secondPlaceLat
        secondLocation.longitude = secondPlaceLon

        val meters = firstLocation.distanceTo(secondLocation).toDouble()

        return convertMetersToMiles(meters)
    }

    override fun convertMetersToMiles(meters: Double): Double {
        return meters * MILES_IN_METER
    }
}