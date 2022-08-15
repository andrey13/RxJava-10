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
}
