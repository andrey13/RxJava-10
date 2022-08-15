package com.raywenderlich.android.ourplanet

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.raywenderlich.android.ourplanet.model.EOEvent
import com.raywenderlich.android.ourplanet.model.EONET

class EventAdapter(private val events: MutableList<EOEvent>)
  : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_event))
  }

  override fun getItemCount() = events.size

  fun updateEvents(events: List<EOEvent>?) {
    this.events.clear()
    this.events.addAll(events ?: emptyList())
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(events[position])
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val eventTitle = itemView.findViewById<TextView>(R.id.eventTitle)
    private val eventDate = itemView.findViewById<TextView>(R.id.eventDate)
    private val eventDescription = itemView.findViewById<TextView>(R.id.eventDescription)

    fun bind(event: EOEvent) {
      eventTitle.text = event.title
      eventDate.text = if (event.closeDate != null) {
        EONET.displayFormatter.format(event.closeDate)
      } else {
        ""
      }
      eventDescription.visibility = if (event.description.isEmpty()) {
        View.GONE
      } else {
        View.VISIBLE
      }
      eventDescription.text = event.description
    }
  }
}

