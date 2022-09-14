package com.ecemsevvalcinar.foodapp.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ecemsevvalcinar.foodapp.model.Food

@Database(entities = arrayOf(Food::class), version = 1)
abstract class FoodDatabase: RoomDatabase() {

    abstract fun foodDao(): FoodDAO

    // Singleton -> icerisinden tek bir object olusturulan sinif, object coktan olusturulmussa o object kullanilir, secilir
    // projenin her yerinden kullanilabilir

    companion object{
        @Volatile private var instance: FoodDatabase? = null

        // volatile -> volatile olarak tanimlananlar diger threadlere de gorunur olur
        // bu sinifi singleton yapma amaci da farkli threadlerle calisabilmek

        // invoke -> instance var ise varolani kullanir, yok ise olusturulur
        // sync olmasi -> ayni anda 2 thread bu objecte ulasamaz, farkli zamanlarda ulasabilirler tabi

        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){

            instance?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,FoodDatabase::class.java,"fooddatabase").build()

    }
}