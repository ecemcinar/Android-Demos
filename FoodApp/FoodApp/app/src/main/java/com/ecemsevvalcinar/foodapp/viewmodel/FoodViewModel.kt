package com.ecemsevvalcinar.foodapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ecemsevvalcinar.foodapp.model.Food
import com.ecemsevvalcinar.foodapp.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodViewModel(application: Application): BaseViewModel(application)  {

    val foodLiveData = MutableLiveData<Food>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food
        }
    }

}