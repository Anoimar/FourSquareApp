package com.thernat.foursquareapp.data.source

import com.thernat.foursquareapp.api.json.Venue
import io.reactivex.Single

/**
 * Created by m.rafalski on 01/06/2019.
 */
interface VenuesDataSource {

    fun getVenues(latLng: String): Single<List<Venue>>


}