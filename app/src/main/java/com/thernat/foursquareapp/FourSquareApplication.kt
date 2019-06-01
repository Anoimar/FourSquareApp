package com.thernat.foursquareapp

import android.app.Activity
import javax.inject.Inject
import dagger.android.AndroidInjector
import android.app.Application
import com.thernat.foursquareapp.di.DaggerAppComponent

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector

/**
 * Created by m.rafalski on 01/06/2019.
 */
class FourSquareApplication: Application(), HasActivityInjector{

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}