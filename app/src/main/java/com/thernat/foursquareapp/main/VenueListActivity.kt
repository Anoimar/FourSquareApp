package com.thernat.foursquareapp.main

import android.os.Bundle
import android.util.Log
import com.thernat.foursquareapp.R
import com.thernat.foursquareapp.api.json.Venue
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class VenueListActivity : DaggerAppCompatActivity(),VenueListContract.View {

    @Inject
    lateinit var presenter: VenueListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun displayVenues(venues: List<Venue>) {
        Log.i("test",venues.toString())
    }

    override fun displayError() {

    }

    override fun showLoading(show: Boolean) {

    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.newLocationAcquired(54.3288,18.6097)
    }

    override fun onPause() {
        presenter.dropView()
        super.onPause()
    }

}
