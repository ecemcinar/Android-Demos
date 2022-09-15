package com.ecemsevvalcinar.foodapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ecemsevvalcinar.foodapp.model.Food
import com.ecemsevvalcinar.foodapp.service.FoodDatabase
import kotlinx.coroutines.launch

class FoodViewModel(application: Application): BaseViewModel(application)  {
    lateinit var ingredientsArray: Array<String>
    val foodLiveData = MutableLiveData<Food>()

    // food fragmentta bastirdiklarimi db'den aliyorum
    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            val food = dao.getFood(uuid)
            foodLiveData.value = food

            // virgule gore split edip spinnerda bastirmak icin
            ingredientsArray = splitIngredients(food.ingredients)

        }
    }

     private fun splitIngredients(ingredients: String): Array<String>{
        val delim = ","
        var splitedList = ingredients.split(delim)
        return splitedList.toTypedArray()
    }


}