package com.raywenderlich.android.ourplanet

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

  private val adapter = CategoriesAdapter(mutableListOf())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_categories)

    title = "${getString(R.string.app_name)} - ${getString(R.string.categories_title)}"

    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter

    val viewModel: CategoriesViewModel by viewModels()

    viewModel.categoriesLiveData.observe(this, Observer { categories ->
      adapter.updateCategories(categories)
    })

    viewModel.startDownload()
  }
}
