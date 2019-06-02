package com.thernat.foursquareapp.main

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.thernat.foursquareapp.R
import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.main.adapter.VenueAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class VenueListActivity : DaggerAppCompatActivity(),VenueListContract.View,EasyPermissions.PermissionCallbacks {

    @Inject
    lateinit var presenter: VenueListContract.Presenter

    @Inject
    lateinit var venueAdapter: VenueAdapter

    companion object {
        private const val REQUEST_CODE_LOCATION_PERM = 123
    }



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

    override fun displayLocationError() {
        showAlert(getString(R.string.location_not_acquired))
    }

    override fun displayNetworkError() {
        showAlert(getString(R.string.download_failed))
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
    }

    override fun displayNoLocationPermissionWarning() {
        showAlert(getString(R.string.location_permission_denied))
    }

    private fun showAlert(alertContent: String) {
        AlertDialog.Builder(this).create().run {
         setMessage(alertContent)
            setButton(AlertDialog.BUTTON_NEUTRAL,getString(R.string.button_neutral)){
                    dialog, _ -> dialog.dismiss()
            }
            show()
        }
    }

    override fun askForLocationPermissions() {
        EasyPermissions.requestPermissions( this,getString(R.string.permission_location_info),
            REQUEST_CODE_LOCATION_PERM,
            Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun isPermissionGranted(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onPause() {
        presenter.dropView()
        super.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == REQUEST_CODE_LOCATION_PERM){
            presenter.locationPermissionDenied()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if(requestCode == REQUEST_CODE_LOCATION_PERM){
            presenter.locationPermissionGranted()
        }
    }
}
