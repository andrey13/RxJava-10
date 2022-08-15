package com.raywenderlich.android.ourplanet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.ourplanet.model.EOCategory
import com.raywenderlich.android.ourplanet.model.EONET
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoriesViewModel : ViewModel() {

  val categoriesLiveData = MutableLiveData<List<EOCategory>>()

  private val disposables = CompositeDisposable()

  fun startDownload() {
    EONET.fetchCategories()
      .map { response ->
        val categories = response.categories
        categories.mapNotNull { EOCategory.fromJson(it) }
      }
      .share()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe {
        categoriesLiveData.value = it
      }
      .addTo(disposables)
  }

  override fun onCleared() {
    super.onCleared()
    disposables.dispose()
  }
}
