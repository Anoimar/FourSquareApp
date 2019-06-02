package com.thernat.foursquareapp.utils.location

import android.location.Location
import javax.inject.Inject

/**
 * Created by m.rafalski on 01/06/2019.
 */
class RequestParamFormatter @Inject constructor() {

    fun convertLatitudeAndLongitudeToQueryParam(location: Location): String{
        return "${location.latitude},${location.longitude}"
    }
}