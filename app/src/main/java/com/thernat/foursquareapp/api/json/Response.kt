package com.thernat.foursquareapp.api.json

data class Response(
    val confident: Boolean,
    val venues: List<Venue>
)