package com.thernat.foursquareapp.utils.location

import javax.inject.Inject

/**
 * Created by m.rafalski on 01/06/2019.
 */
class RequestParamFormatter @Inject constructor() {

    fun convertLatitudeAndLongitudeToQueryParam(latitude: Double, longitude: Double): String{
        return "$latitude,$longitude"
    }
}