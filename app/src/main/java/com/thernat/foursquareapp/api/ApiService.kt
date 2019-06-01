package com.thernat.foursquareapp.api

import com.thernat.foursquareapp.BuildConfig
import com.thernat.foursquareapp.api.json.ApiResponse
import dagger.Reusable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Reusable
interface ApiService {

    @GET("search")
    fun getVenues(@Query("ll") latLng: String,
                  @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
                  @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
                  @Query("v") version: String = BuildConfig.API_VERSION
    ): Single<ApiResponse>
}