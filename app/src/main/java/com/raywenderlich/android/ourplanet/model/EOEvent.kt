package com.raywenderlich.android.ourplanet.model

import android.os.Parcelable
import com.raywenderlich.android.ourplanet.AnyMap
import kotlinx.android.parcel.Parcelize
import java.net.URL
import java.util.*

@Parcelize
data class EOEvent(
    val id: String,
    val title: String,
    val description: String,
    val link: URL?,
    val closeDate: Date?,
    val categories: List<Int>
) : Parcelable {
  companion object {
    @Suppress("UNCHECKED_CAST")
    fun fromJson(json: AnyMap): EOEvent? {
      val id = json["id"] as? String
      val title = json["title"] as? String
      val description = json["description"] as? String
      val linkString = json["link"] as? String
      val closeString = json["closed"] as? String
      val categoriesList = json["categories"] as? List<AnyMap>
      if (id == null || title == null || description == null
          || linkString == null || categoriesList == null) {
        return null
      }

      val closeDate = if (closeString == null) {
        null
      } else {
        EONET.formatter.parse(closeString)
      }
      val link = URL(linkString)
      val categories = categoriesList.mapNotNull {
        val categoryId = (it["id"] as? Double)?.toInt()
        categoryId
      }

      return EOEvent(id, title, description, link, closeDate, categories)
    }

    val compareByDates = Comparator<EOEvent> { x, y ->
      if (x.closeDate == null || y.closeDate == null) {
        0
      } else {
        x.closeDate.compareTo(y.closeDate)
      }
    }
  }
}
