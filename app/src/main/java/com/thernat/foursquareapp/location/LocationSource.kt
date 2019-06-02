package com.thernat.foursquareapp.location

import io.reactivex.Single

/**
 * Created by m.rafalski on 02/06/2019.
 */
interface LocationSource {

    fun getLocation(): Single<String>
}