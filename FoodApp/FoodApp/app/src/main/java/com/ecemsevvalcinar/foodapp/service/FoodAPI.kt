package com.ecemsevvalcinar.foodapp.service

import com.ecemsevvalcinar.foodapp.model.Food
import com.ecemsevvalcinar.foodapp.util.URL_EXTENSION
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {


    // extension -->
    @GET(URL_EXTENSION)
    fun getFoods(): Single<List<Food>>

    // Single RxJava'ya aittir
    // Single, observable'dır ve tek bir call yapar ve durur
}