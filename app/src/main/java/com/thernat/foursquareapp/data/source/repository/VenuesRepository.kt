package com.thernat.foursquareapp.data.source.repository

import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.data.source.VenuesDataSource
import com.thernat.foursquareapp.data.source.remote.Remote
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Singleton
class VenuesRepository @Inject constructor(@Remote private val remoteVenuesDataSource: VenuesDataSource): VenuesDataSource {

    private var cachedVenues: LinkedHashMap<String,List<Venue>> = LinkedHashMap()


    override fun getVenues(latLng: String): Single<List<Venue>> {
        val venuesForPositionInCache: List<Venue>? = cachedVenues[latLng]
        if(venuesForPositionInCache != null){
            return Single.just(venuesForPositionInCache)
        }
        return getAndCacheRemoteVenues(latLng)
    }

    private fun getAndCacheRemoteVenues(latLng: String): Single<List<Venue>> {
        return remoteVenuesDataSource
            .getVenues(latLng)
            .flatMap {
                cacheVenues(latLng,it)
                Single.just(it)
            }
    }

    private fun cacheVenues(cacheKey: String, venues: List<Venue>) {
        cachedVenues[cacheKey] = venues
    }


}