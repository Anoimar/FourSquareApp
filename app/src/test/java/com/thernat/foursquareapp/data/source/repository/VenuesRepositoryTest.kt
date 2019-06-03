package com.thernat.foursquareapp.data.source.repository

import com.thernat.foursquareapp.api.json.Location
import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.data.source.VenuesDataSource
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by m.rafalski on 03/06/2019.
 */
class VenuesRepositoryTest {

    companion object {
        const val NEW_YORK_GEO_COORDINATES = "40.7128,-74.0060"
    }

    private lateinit var firstCallTestObserver: TestObserver<List<Venue>>

    private lateinit var secondCallTestObserver: TestObserver<List<Venue>>


    private lateinit var venuesRepository: VenuesRepository

    @Mock
    private lateinit var venuesRemoteDataSource: VenuesDataSource

    private val venuesList: List<Venue> = listOf(
        Venue("4a676321f964a52051c91fe3", Location("260 E Broadway",200),"New York City Hall"),
        Venue("4dd77d22b3adc64ae076925d", Location("new york",439),"St comunity beach"),
        Venue("4e923f8293adf0b00053601a", Location("52 Chambers St",312),"Department of Education")
        )


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        venuesRepository = VenuesRepository(venuesRemoteDataSource)
        firstCallTestObserver = TestObserver()
    }

    @Test
    fun shouldReturnRemoteDataOnFirstCallFromNewLocation() {
        givenRemoteRepositoryWillReturnVenuesList()
        whenSubscriptionIsSetOnRepository()
        thenRemoteDataSourceIsCalledAndVenuesListIsReturned()
    }



    private fun givenRemoteRepositoryWillReturnVenuesList() {
        given(venuesRemoteDataSource
            .getVenues(NEW_YORK_GEO_COORDINATES))
            .willReturn(Single.just(venuesList))
    }

    private fun whenSubscriptionIsSetOnRepository() {
        venuesRepository.getVenues(NEW_YORK_GEO_COORDINATES)
            .subscribe(firstCallTestObserver)
    }

    private fun thenRemoteDataSourceIsCalledAndVenuesListIsReturned() {
        then(venuesRemoteDataSource)
            .should()
            .getVenues(NEW_YORK_GEO_COORDINATES)
        firstCallTestObserver.assertValue(venuesList)
    }

    @Test
    fun shouldUseDataFromCacheOnSecondCallFromTheSameLocation() {
        givenRemoteRepositoryWillReturnVenuesList()
        whenTwoSubscriptionsAreSetOnRepository()
        thenRemoteDataSourceIsCalledOnlyOnceAndSecondSubscriptionUsesDataFromCache()
    }

    private fun whenTwoSubscriptionsAreSetOnRepository() {
        secondCallTestObserver = TestObserver()
        venuesRepository.getVenues(NEW_YORK_GEO_COORDINATES)
            .subscribe(firstCallTestObserver)
        venuesRepository.getVenues(NEW_YORK_GEO_COORDINATES)
            .subscribe(secondCallTestObserver)
    }

    private fun thenRemoteDataSourceIsCalledOnlyOnceAndSecondSubscriptionUsesDataFromCache() {
        then(venuesRemoteDataSource)
            .should(Mockito.times(1))
            .getVenues(NEW_YORK_GEO_COORDINATES)
        firstCallTestObserver.assertValue(venuesList)
        secondCallTestObserver.assertValue(venuesList)
    }
}