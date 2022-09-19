package com.ecemsevvalcinar.foodapp.service

import com.ecemsevvalcinar.foodapp.model.Food
import com.ecemsevvalcinar.foodapp.util.BASE_URL
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class FoodService {

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Json file icin
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava kullanilacagini belirttim
        .build()
        .create(FoodAPI::class.java)

    fun getData(): Single<List<Food>>{
        return api.getFoods()
    }
}