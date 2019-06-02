package com.thernat.foursquareapp.main

import android.os.Bundle
import android.util.Log
import com.thernat.foursquareapp.R
import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.main.adapter.VenueAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class VenueListActivity : DaggerAppCompatActivity(),VenueListContract.View {

    @Inject
    lateinit var presenter: VenueListContract.Presenter

    @Inject
    lateinit var venueAdapter: VenueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lvVenues.adapter = venueAdapter
    }

    override fun displayVenues(venues: List<Venue>) {
        venueAdapter.replaceData(venues)
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
