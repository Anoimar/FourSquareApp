package com.thernat.foursquareapp.data.source.di

import com.thernat.foursquareapp.api.ApiService
import com.thernat.foursquareapp.data.source.VenuesDataSource
import com.thernat.foursquareapp.data.source.remote.Remote
import com.thernat.foursquareapp.data.source.remote.VenuesRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Module
class VenuesRepositoryModule {

    @Singleton
    @Provides
    @Remote
    internal fun provideVenuesRepository(apiService: ApiService): VenuesDataSource {
        return VenuesRemoteDataSource(apiService)
    }
}