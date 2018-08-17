package com.mumsapp.domain.utils

interface LocationHelper {

    fun calculateDistanceInMiles(firstPlaceLat: Double, firstPlaceLon: Double,
                                 secondPlaceLat: Double, secondPlaceLon: Double): Double

    fun convertMetersToMiles(meters: Double): Double
}