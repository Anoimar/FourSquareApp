package com.thernat.foursquareapp.main

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


    override fun newLocationAcquired(latitude: Double, longitude: Double) {
        venuesRepository
            .getVenues(requestParamFormatter.convertLatitudeAndLongitudeToQueryParam(latitude,longitude))
            .subscribeOn(schedulerProvider.computation())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                view?.displayVenues(it)
            },{
                view?.displayError()
            }).apply {
                compositeDisposable.add(this)
            }
    }


    override fun takeView(view: VenueListContract.View) {
        this.view = view
    }

    override fun dropView() {
        compositeDisposable.clear()
        view = null
    }
}