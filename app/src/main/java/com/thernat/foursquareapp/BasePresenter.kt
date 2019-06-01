package com.thernat.foursquareapp

/**
 * Created by m.rafalski on 01/06/2019.
 */
interface BasePresenter<T> {

    fun takeView(view: T)

    fun dropView()
}