package com.thernat.foursquareapp.di

import com.thernat.foursquareapp.FourSquareApplication
import com.thernat.foursquareapp.api.di.NetModule
import com.thernat.foursquareapp.data.source.di.VenuesRepositoryModule
import com.thernat.foursquareapp.location.LocationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    ActivityBindingModule::class, AppModule::class, NetModule::class, LocationModule::class,
    VenuesRepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: FourSquareApplication): Builder

        fun build(): AppComponent
    }

    fun inject(application: FourSquareApplication)
}