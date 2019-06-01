package com.thernat.foursquareapp.main.di

import com.thernat.foursquareapp.di.ActivityScoped
import com.thernat.foursquareapp.main.VenueListContract
import com.thernat.foursquareapp.main.VenueListPresenter
import dagger.Binds
import dagger.Module

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Module
abstract class VenueListModule {

    @ActivityScoped
    @Binds
    internal abstract fun venueListPresenter(presenter: VenueListPresenter): VenueListContract.Presenter
}