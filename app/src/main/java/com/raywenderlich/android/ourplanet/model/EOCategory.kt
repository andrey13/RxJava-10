package com.raywenderlich.android.ourplanet.model

import android.os.Parcelable
import com.raywenderlich.android.ourplanet.AnyMap
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EOCategory(
    val id: Int,
    val title: String,
    val description: String,
    val endpoint: String,
    val events: MutableList<EOEvent> = mutableListOf()) : Parcelable {

  companion object {
    fun fromJson(json: AnyMap): EOCategory? {
      val id = (json["id"] as? Double)?.toInt()
      val title = json["title"] as? String
      val description = json["description"] as? String
      if (id == null || title == null || description == null) {
        return null
      }

      return EOCategory(id, title, description, "${EONET.CATEGORIES_ENDPOINT}/$id")
    }
  }
}
