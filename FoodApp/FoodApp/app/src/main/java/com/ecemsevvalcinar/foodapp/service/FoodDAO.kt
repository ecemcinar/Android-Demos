package com.ecemsevvalcinar.foodapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ecemsevvalcinar.foodapp.model.Food

@Dao
interface FoodDAO {
    // dao -> data access object

    @Insert
    suspend fun insertAll(vararg food: Food): List<Long>

    // vararg -> birden fazla country objectini fonksiyon icine vermemizi saglar
    // list<long> -> primary keyler return edilecek


    @Query("SELECT * FROM food")
    suspend fun getAllFoods(): List<Food>

    @Query("SELECT * FROM food WHERE uuId = :countryId")
    suspend fun getFood(countryId: Int): Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFoods()
}