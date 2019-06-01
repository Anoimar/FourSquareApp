package com.thernat.foursquareapp.data.source.remote

import com.thernat.foursquareapp.api.ApiService
import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.data.source.VenuesDataSource
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by m.rafalski on 01/06/2019.
 */
class VenuesRemoteDataSource @Inject constructor(private val apiService: ApiService): VenuesDataSource {

    override fun getVenues(latLng: String): Single<List<Venue>> {
        return apiService.getVenues(latLng)
            .flatMap {
                Single.just(it.response.venues)
            }
    }


}