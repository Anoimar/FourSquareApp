package com.thernat.foursquareapp.di

import android.content.Context
import com.thernat.foursquareapp.FourSquareApplication
import dagger.Binds
import dagger.Module

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Module
abstract class AppModule{

    @Binds
    internal abstract fun bindContext(application: FourSquareApplication): Context

}