package com.ecemsevvalcinar.foodapp.service

import com.ecemsevvalcinar.foodapp.model.Food
import io.reactivex.Single
import retrofit2.http.GET

interface FoodAPI {


    // extension -->
    @GET("ecemcinar/Android-Demos/main/FoodApp/DataSetFoods/fooddataset.json")
    fun getFoods(): Single<List<Food>>

    // Single RxJava'ya aittir
    // Single, observable'dır ve tek bir call yapar ve durur
}