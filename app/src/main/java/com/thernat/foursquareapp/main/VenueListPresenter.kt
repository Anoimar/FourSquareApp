package com.thernat.foursquareapp.main

import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.data.source.repository.VenuesRepository
import com.thernat.foursquareapp.location.LocationSource
import com.thernat.foursquareapp.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by m.rafalski on 01/06/2019.
 */
class VenueListPresenter @Inject constructor(private val venuesRepository: VenuesRepository,private val schedulerProvider: BaseSchedulerProvider,private val locationSource: LocationSource): VenueListContract.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var view: VenueListContract.View? = null

    private var latitudeAndLongitude: String? = null

    var filter = ""


    override fun takeView(view: VenueListContract.View) {
        this.view = view
        if(latitudeAndLongitude != null){
            loadVenues()
        } else {
            getLocation()
        }

    }

    private fun getLocation() {
        view?.let { view ->
            if(view.isPermissionGranted()){
                getUserLocation()
            } else {
                view.askForLocationPermissions()
            }
        }
    }

    private fun getUserLocation(){
        view?.showLoading(true)
        locationSource
            .getLocation()
            .subscribe(
                {
                    latitudeAndLongitude = it
                    loadVenues()
                },{
                    view?.displayLocationError()
                }
            ).apply {
                compositeDisposable.add(this)
            }
    }

    override fun setNewFilter(query: String) {
        this.filter = query
        loadVenues()
    }

    private fun loadVenues() {
        latitudeAndLongitude?.let {
            view?.showLoading(true)
            venuesRepository
                .getVenues(it)
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe({ venues ->
                    filterAndDisplayVenues(venues)
                    view?.showLoading(false)
                },{
                    view?.displayNetworkError()
                    view?.showLoading(false)
                }).apply {
                    compositeDisposable.add(this)
                }
        }
    }

    private fun filterAndDisplayVenues(venues: List<Venue>) {
      venues.asSequence()
            .filter {  it.name.startsWith(filter,true)}
            .toList().let {filteredVenues ->
              if(filteredVenues.isEmpty()){
                  view?.displayNoResults()
              } else {
                  view?.displayVenues(filteredVenues)
              }
          }
    }


    override fun locationPermissionGranted() {
        getUserLocation()
    }

    override fun locationPermissionDenied() {
        view?.displayNoLocationPermissionWarning()
    }

    override fun dropView() {
        compositeDisposable.clear()
        view = null
    }
}