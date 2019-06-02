package com.thernat.foursquareapp.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
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
        initViews()
    }

    private fun initViews() {
        lvVenues.adapter = venueAdapter
        svFindVenue.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                presenter.setNewFilter(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                presenter.setNewFilter(newText)
                return true
            }

        } )
    }

    override fun displayVenues(venues: List<Venue>) {
        venueAdapter.replaceData(venues)
        tvNoResults.visibility = View.INVISIBLE
    }

    override fun displayError() {
        showAlert()
    }

    override fun showLoading(show: Boolean) {
        if(show){
            pbLoading.visibility = View.VISIBLE
        } else {
            pbLoading.visibility = View.INVISIBLE
        }

    }

    override fun displayNoResults() {
        venueAdapter.replaceData(listOf())
        tvNoResults.visibility = View.VISIBLE
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

    private fun showAlert(){
        AlertDialog.Builder(this).create().run {
         setMessage(getString(R.string.download_failed))
            setButton(AlertDialog.BUTTON_NEUTRAL,getString(R.string.button_neutral)){
                    dialog, _ -> dialog.dismiss()
            }
            show()
        }
    }
}
