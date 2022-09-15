package com.ecemsevvalcinar.foodapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.ecemsevvalcinar.foodapp.model.Food
import com.ecemsevvalcinar.foodapp.service.FoodDatabase
import com.ecemsevvalcinar.foodapp.service.FoodService
import com.ecemsevvalcinar.foodapp.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {

    private val foodService = FoodService()
    private val disposable = CompositeDisposable()
    private var timeCustomPreferences = CustomSharedPreferences(getApplication())
    private var resreshTime = 5*60*1000*1000*1000L


    val foods = MutableLiveData<List<Food>>()
    val foodError = MutableLiveData<Boolean>()
    val foodLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = timeCustomPreferences.getTime()

        // 5 dakikada bir yenileyecek
        if(updateTime != null && updateTime!= 0L && System.nanoTime() - updateTime < resreshTime){
            getDataFromSQLite()
        }
        else{
            getDataFromAPI()
            Toast.makeText(getApplication(),"Foods from API", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getDataFromSQLite(){
        foodLoading.value = true
        launch {
            val foodsList = FoodDatabase(getApplication()).foodDao().getAllFoods()
            showFoods(foodsList)
            Toast.makeText(getApplication(),"Foods from SQLite",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshDataFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromAPI(){
        foodLoading.value = true // progres bar shows up

        disposable.add(
            foodService.getData()
                .subscribeOn(Schedulers.newThread()) // asenkron bir sekilde islem yapar, arka planda
                .observeOn(AndroidSchedulers.mainThread()) // nerede gozlemleyecegimiz, main threadde yapilacak
                .subscribeWith(object: DisposableSingleObserver<List<Food>>(){
                    // observable'da bu ozellik yok,
                    override fun onSuccess(t: List<Food>) {
                        saveToSQLite(t)
                        Toast.makeText(getApplication(),"Foods from API",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        foodError.value = true
                        foodLoading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun saveToSQLite(t: List<Food>){ // main threadde yapilmayacak, yapilirsa ui blocklanir
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFoods()

            // toTypedArray -> listedeki elemanlari individual hale getirir, tek tek gonderir
            val listLong = dao.insertAll(*t.toTypedArray())
            var i =0
            while (i<t.size){
                t[i].uuid = listLong[i].toInt()
                i++
            }
            showFoods(t)
        }
        timeCustomPreferences.saveTime(System.nanoTime())
    }

    private fun showFoods(t:List<Food>){
        foods.value = t
        foodLoading.value = false
        foodError.value  = false
    }

    override fun onCleared() { // hafizayi verimli kullanmak icin
        super.onCleared()
        disposable.clear()
    }
}