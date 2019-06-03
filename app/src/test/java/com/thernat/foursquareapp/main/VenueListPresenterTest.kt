package com.thernat.foursquareapp.main

import com.thernat.foursquareapp.api.json.Location
import com.thernat.foursquareapp.api.json.Venue
import com.thernat.foursquareapp.data.source.repository.VenuesRepository
import com.thernat.foursquareapp.location.LocationSource
import com.thernat.foursquareapp.utils.schedulers.ImmediateSchedulerProvider
import io.reactivex.Single
import org.junit.Test
import org.mockito.BDDMockito.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by m.rafalski on 03/06/2019.
 */
class VenueListPresenterTest {

    private lateinit var venueListPresenter: VenueListPresenter

    @Mock
    private lateinit var  venuesRepository: VenuesRepository

    @Mock
    private lateinit var view: VenueListContract.View

    @Mock
    private lateinit var locationSource: LocationSource

    companion object {
        const val JOHANNESBURG_GEO_COORDINATES = "-26.195246,28.034088"
        const val FILTER_NOT_MATCHING_ANY_VENUE_NAME = "Pyramids of Giza"
    }

    private val venuesList: List<Venue> = listOf(
        Venue("4a676321f964a52051c91fe3", Location("54 De Korte Street",200),"Audi Centre Johannesburg"),
        Venue("4dd77d22b3adc64ae076925d", Location("Juta Street",439),"Rosebank College"),
        Venue("5379c448498ed94f0209bb78", Location("70 Juta St. Braamfontein",312),"The Language Lab")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(locationSource.getLocation()).willReturn(Single.just(JOHANNESBURG_GEO_COORDINATES))
        venueListPresenter = VenueListPresenter(venuesRepository,ImmediateSchedulerProvider(),locationSource)
    }

    @Test
    fun shouldGetNewLocationBeforeLoadingVenuesIfLocationPermissionIsGrantedAndNoCachedDataIsPresent() {
        givenLocationPermissionGranted()
        givenVenueRepositoryWillReturnListWithVenues()
        whenPresenterTakesView()
        thenCallToLocationDataSourceIsMade()
        thenAllVenuesFromRepositoryAreDisplayed()
        thenProgressBarIsHidden()
    }


    private fun thenProgressBarIsHidden() {
      then(view).should().showLoading(false)
    }


    private fun givenLocationPermissionGranted() {
        given(view.isPermissionGranted()).willReturn(true)
    }

    private fun givenVenueRepositoryWillReturnListWithVenues() {
        given(venuesRepository.getVenues(JOHANNESBURG_GEO_COORDINATES)).willReturn(Single.just(venuesList))
    }


    private fun whenPresenterTakesView() {
        venueListPresenter.takeView(view)
    }

    private fun thenCallToLocationDataSourceIsMade() {
        then(locationSource).should().getLocation()
    }

    private fun thenAllVenuesFromRepositoryAreDisplayed() {
        then(view).should().displayVenues(venuesList)
    }

    @Test
    fun shouldAskForLocationPermissionIfNotGranted() {
        givenLocationPermissionIsNotGranted()
        whenPresenterTakesView()
        thenPermissionRequestShouldBeDisplayed()
    }


    private fun givenLocationPermissionIsNotGranted() {
        given(view.isPermissionGranted()).willReturn(false)
    }

    private fun thenPermissionRequestShouldBeDisplayed() {
        then(view).should().askForLocationPermissions()
    }

    @Test
    fun shouldShowOnlyFilteredVenuesAfterSettingFilterWhenFilterMatchesSomeVenues() {
        givenLocationPermissionGranted()
        givenVenueRepositoryWillReturnListWithVenues()
        whenFilterWithNameOfFirstVenueIsSet()
        thenOnlyFirstVenueIsDisplayed()
    }

    private fun whenFilterWithNameOfFirstVenueIsSet() {
        whenPresenterTakesView()
        venueListPresenter.setNewFilter(venuesList.first().name)
    }

    private fun thenOnlyFirstVenueIsDisplayed() {
        then(view).should().displayVenues(listOf(venuesList.first()))
    }

    @Test
    fun shouldShowNoResultsAfterSettingFilterNotMatchingAnyVenues() {
        givenLocationPermissionGranted()
        givenVenueRepositoryWillReturnListWithVenues()
        whenFilterWithVenueNameNotPresentInRepositoryIsSet()
        thenNoResultsViewIsShown()
    }


    private fun whenFilterWithVenueNameNotPresentInRepositoryIsSet() {
        whenPresenterTakesView()
        venueListPresenter.setNewFilter(FILTER_NOT_MATCHING_ANY_VENUE_NAME)
    }

    private fun thenNoResultsViewIsShown() {
        then(view.displayNoResults())
    }

    @Test
    fun shouldShowErrorWhenFailedToGetVenuesFromRemote() {
        givenLocationPermissionGranted()
        givenVenueRepositoryWillReturnListWithVenues()
        whenPresenterTakesView()
        thenCallToLocationDataSourceIsMade()
        thenAllVenuesFromRepositoryAreDisplayed()
        thenProgressBarIsHidden()
    }
}