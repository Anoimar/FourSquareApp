package com.thernat.foursquareapp.main.di

import com.thernat.foursquareapp.di.ActivityScoped
import com.thernat.foursquareapp.main.VenueListActivity
import com.thernat.foursquareapp.main.VenueListContract
import com.thernat.foursquareapp.main.VenueListPresenter
import com.thernat.foursquareapp.main.adapter.VenueAdapter
import com.thernat.foursquareapp.utils.schedulers.BaseSchedulerProvider
import com.thernat.foursquareapp.utils.schedulers.SchedulerProvider
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Module
abstract class VenueListModule {

    @ActivityScoped
    @Binds
    internal abstract fun venueListPresenter(presenter: VenueListPresenter): VenueListContract.Presenter

    @Module
    companion object {

        @JvmStatic
        @Provides
        @ActivityScoped
        fun provideVenueAdapter(venueListActivity: VenueListActivity): VenueAdapter {
            return VenueAdapter(
                venueListActivity,
                arrayListOf()
            )
        }

        @JvmStatic
        @Provides
        @ActivityScoped
        fun provideBaseSchedulerProvider(): BaseSchedulerProvider {
            return SchedulerProvider()
        }

    }
}