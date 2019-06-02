package com.thernat.foursquareapp.main

import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.data.source.repository.VenuesRepository
import com.thernat.foursquareapp.utils.location.RequestParamFormatter
import com.thernat.foursquareapp.utils.schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by m.rafalski on 01/06/2019.
 */
class VenueListPresenter @Inject constructor(private val venuesRepository: VenuesRepository, private val requestParamFormatter: RequestParamFormatter, private val schedulerProvider: SchedulerProvider): VenueListContract.Presenter {

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
                getLocationFromProvider()
            } else {
                view.askForLocationPermissions()
            }
        }
    }

    private fun getLocationFromProvider(){
        latitudeAndLongitude = requestParamFormatter.convertLatitudeAndLongitudeToQueryParam(54.3288,18.6097)
        loadVenues()
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
                    view?.displayError()
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
        getLocationFromProvider()
    }

    override fun locationPermissionDenied() {
        view?.displayNoLocationPermissionWarning()
    }

    override fun dropView() {
        compositeDisposable.clear()
        view = null
    }
}