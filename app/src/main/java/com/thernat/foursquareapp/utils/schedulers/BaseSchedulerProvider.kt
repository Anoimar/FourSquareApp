package com.thernat.foursquareapp.utils.schedulers

import io.reactivex.Scheduler

/**
 * Created by m.rafalski on 01/06/2019.
 */
interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}