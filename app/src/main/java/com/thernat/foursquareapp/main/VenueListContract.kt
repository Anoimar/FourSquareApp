package com.thernat.foursquareapp.main

import com.thernat.foursquareapp.BasePresenter
import com.thernat.foursquareapp.BaseView
import com.thernat.foursquareapp.api.json.Venue

/**
 * Created by m.rafalski on 01/06/2019.
 */
interface VenueListContract {

    interface View : BaseView<Presenter> {

        fun displayVenues(venues: List<Venue>)

        fun displayNetworkError()

        fun showLoading(show: Boolean)

        fun displayNoResults()

        fun isPermissionGranted(): Boolean

        fun askForLocationPermissions()

        fun displayNoLocationPermissionWarning()

        fun displayLocationError()

    }

    interface Presenter : BasePresenter<View> {

        fun setNewFilter(query: String)

        fun locationPermissionGranted()

        fun locationPermissionDenied()
    }
}