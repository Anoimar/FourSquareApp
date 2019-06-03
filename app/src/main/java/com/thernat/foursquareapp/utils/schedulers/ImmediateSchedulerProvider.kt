package com.thernat.foursquareapp.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by m.rafalski on 03/06/2019.
 */
/**
 * Created for testing. Always returns TrampolineScheduler,
 * all Schedulers run synchronously
 */
class ImmediateSchedulerProvider: BaseSchedulerProvider {


    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

}