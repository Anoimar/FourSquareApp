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

        fun displayError()

        fun showLoading(show: Boolean)


    }

    interface Presenter : BasePresenter<View> {

        fun newLocationAcquired(latitude: Double, longitude: Double)
    }
}