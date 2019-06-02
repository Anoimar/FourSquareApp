package com.thernat.foursquareapp.location

import android.content.Context
import android.location.LocationManager
import com.thernat.foursquareapp.utils.location.RequestParamFormatter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by m.rafalski on 02/06/2019.
 */
@Module
class LocationModule {

    @Singleton
    @Provides
    internal fun provideLocationSource(context: Context,requestParamFormatter: RequestParamFormatter): LocationSource {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationProvider(locationManager,requestParamFormatter)
    }

}