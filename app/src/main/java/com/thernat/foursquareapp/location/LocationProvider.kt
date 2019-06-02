package com.thernat.foursquareapp.location

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.thernat.foursquareapp.utils.location.RequestParamFormatter
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by m.rafalski on 02/06/2019.
 */
@Singleton
class LocationProvider @Inject constructor(private val locationManager: LocationManager,private val requestParamFormatter: RequestParamFormatter): LocationSource {

    var locationCache: String? = null

    @SuppressLint("MissingPermission")
    override fun getLocation(): Single<String> {
        if(locationCache != null){
            return Single.just(locationCache)
        }
        return Single.create { emitter ->
            if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                emitter.onError(NetworkProviderDisabledException())

            } else {
                val locationListener = object : LocationListener {

                    override fun onLocationChanged(location: Location) {
                        val latLng = requestParamFormatter.convertLatitudeAndLongitudeToQueryParam(location)
                        locationCache = latLng
                        emitter.onSuccess(latLng)
                    }

                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle?) {}

                    override fun onProviderEnabled(provider: String) {}

                    override fun onProviderDisabled(provider: String) {
                        emitter.onError(NetworkProviderDisabledException())
                    }
                }
                locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListener, null)
                emitter.setCancellable { locationManager.removeUpdates(locationListener) }
            }
        }
    }

}