package com.thernat.foursquareapp.utils.schedulers

import dagger.Reusable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by m.rafalski on 01/06/2019.
 */
@Reusable
class SchedulerProvider @Inject constructor(): BaseSchedulerProvider  {

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

}