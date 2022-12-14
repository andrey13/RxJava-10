package com.raywenderlich.android.ourplanet.model

import com.raywenderlich.android.ourplanet.EONETApi
import io.reactivex.rxjava3.core.Observable
import java.text.SimpleDateFormat
import java.util.Locale

object EONET {

  const val API = "https://eonet.gsfc.nasa.gov/api/v2.1/"
  private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
  private const val DISPLAY_DATE_FORMAT = "MM/dd/YY"
  const val CATEGORIES_ENDPOINT = "categories"
  const val EVENTS_ENDPOINT = "events"

  val formatter = SimpleDateFormat(DATE_FORMAT, Locale.US)
  val displayFormatter = SimpleDateFormat(DISPLAY_DATE_FORMAT, Locale.US)

  private val eonet by lazy {
    EONETApi.create()
  }

  fun fetchCategories(): Observable<EOCategoriesResponse> {
    return eonet.fetchCategories()
  }

  fun fetchEvents(category: EOCategory, forLastDays: Int = 360):
      Observable<List<EOEvent>>
  {
    val openEvents = events(
      forLastDays, false,
      category.endpoint)

    val closedEvents = events(
      forLastDays, true,
      category.endpoint)

    return Observable.merge(openEvents, closedEvents)
  }

  private fun events(
    forLastDays: Int,
    closed: Boolean,
    endpoint: String
  ): Observable<List<EOEvent>> {
    val status = if (closed) "closed" else "open"
    return EONET.eonet.fetchEvents(endpoint, forLastDays, status)
      .map { response ->
        val events = response.events
        events.mapNotNull { EOEvent.fromJson(it) }
      }
  }

  fun filterEventsForCategory(
    events: List<EOEvent>,
    category: EOCategory
  ): List<EOEvent> {

    return events.filter { event ->
      event.categories.contains(category.id) &&
          !category.events.map { it.id }.contains(event.id)
    }.sortedWith(EOEvent.compareByDates)
  }

}
