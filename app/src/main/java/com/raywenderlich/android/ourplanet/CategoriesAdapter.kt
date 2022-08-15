package com.raywenderlich.android.ourplanet

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.raywenderlich.android.ourplanet.model.EOCategory

class CategoriesAdapter(private val categories: MutableList<EOCategory>)
  : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.inflate(R.layout.list_item_category))
  }

  override fun getItemCount() = categories.size

  fun updateCategories(categories: List<EOCategory>?) {
    this.categories.addAll(categories ?: emptyList())
    notifyDataSetChanged()
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(categories[position])
  }

  class ViewHolder(itemView: View)
    : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var category: EOCategory

    private val title = itemView.findViewById<TextView>(R.id.title)
    private val chevron = itemView.findViewById<ImageView>(R.id.chevron)

    init {
      itemView.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(category: EOCategory) {
      this.category = category
      title.text = "${category.title} (${category.events.size})"
      chevron.visibility = if (category.events.size > 0) {
        View.VISIBLE
      } else {
        View.INVISIBLE
      }
    }

    override fun onClick(view: View) {
      if (category.events.size > 0) {
        val context = view.context
        val intent = EventsActivity.newIntent(context, this.category)
        context.startActivity(intent)
      }
    }
  }
}
