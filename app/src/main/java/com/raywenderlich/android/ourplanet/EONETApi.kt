package com.raywenderlich.android.ourplanet

import com.raywenderlich.android.ourplanet.model.EOCategoriesResponse
import com.raywenderlich.android.ourplanet.model.EOEventsResponse
import com.raywenderlich.android.ourplanet.model.EONET
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EONETApi {
    companion object {
        fun create(): EONETApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(EONET.API)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(EONETApi::class.java)
        }
    }

    @GET(EONET.CATEGORIES_ENDPOINT)
    fun fetchCategories(): Observable<EOCategoriesResponse>

    @GET(EONET.EVENTS_ENDPOINT)
    fun fetchEvents(
        @Query("days") forLastDays: Int,
        @Query("status") status: String
    ): Observable<EOEventsResponse>
}
