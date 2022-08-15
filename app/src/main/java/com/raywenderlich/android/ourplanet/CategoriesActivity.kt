package com.raywenderlich.android.ourplanet

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {

  private lateinit var viewModel: CategoriesViewModel

  private val adapter = CategoriesAdapter(mutableListOf())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_categories)

    title = "${getString(R.string.app_name)} - ${getString(R.string.categories_title)}"

    recyclerView.layoutManager =
      LinearLayoutManager(this)
    recyclerView.adapter = adapter

    viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)

    viewModel.categoriesLiveData.observe(this, Observer { categories ->
      adapter.updateCategories(categories)
    })

    viewModel.startDownload()
  }
}
