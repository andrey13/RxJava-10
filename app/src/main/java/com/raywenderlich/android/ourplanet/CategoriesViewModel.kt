package com.raywenderlich.android.ourplanet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raywenderlich.android.ourplanet.model.EOCategory
import com.raywenderlich.android.ourplanet.model.EONET
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoriesViewModel : ViewModel() {

    val categoriesLiveData = MutableLiveData<List<EOCategory>>()

    private val disposables = CompositeDisposable()

    fun startDownload() {
        val eoCategories = EONET.fetchCategories()
            .map { response ->
                val categories = response.categories
                categories.mapNotNull { EOCategory.fromJson(it) }
            }

        val downloadedEvents = EONET.fetchEvents()

        val updatedCategories = Observables
            .combineLatest(eoCategories, downloadedEvents)
            { categoriesResponse, eventsResponse ->
                categoriesResponse.map { category ->
                    val cat = category.copy()
                    cat.events.addAll(
                        eventsResponse.filter {
                            it.categories.contains(category.id)
                        }
                    )
                    cat
                }
            }

        eoCategories.concatWith(updatedCategories)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { categoriesLiveData.value = it },
                { Log.e("CategoriesViewModel", it.localizedMessage ?: "null") }
            )
            .addTo(disposables)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
