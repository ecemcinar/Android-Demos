package com.ecemsevvalcinar.retrofitkotlin.service

import com.ecemsevvalcinar.retrofitkotlin.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {


    //GET; veri cekmek icin
    //POST; veriyi yazmak icin
    //UPDATE; gunncellemek icin

    //https://raw.githubusercontent.com/ url baz
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json

    //retrofit kurallarindan dolayi, extension kismi buraya yazilir
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //degisikleri gozleyebilmek icin
    suspend fun getData(): Response<List<CryptoModel>> // coroutines ile yaparken

    //fun getData(): Observable<List<CryptoModel>>
    //call yerine usttekini kullan
    //fun getData(): Call<List<CryptoModel>>

}