package com.thernat.foursquareapp.di

import com.thernat.foursquareapp.main.VenueListActivity
import com.thernat.foursquareapp.main.adapter.VenueAdapter
import com.thernat.foursquareapp.main.di.VenueListModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [(VenueListModule::class)])
    internal abstract fun bindsVenueActivity(): VenueListActivity


}