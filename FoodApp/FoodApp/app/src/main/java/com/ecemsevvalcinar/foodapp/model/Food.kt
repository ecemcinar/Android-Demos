package com.ecemsevvalcinar.foodapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity //Entity() seklinde de yapilabilir, tableName gibi parameterlar verilebilir
//data class -> veri tuttugumuz class, veri cekerken kullaniriz
data class Food(

    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("ingredients")
    val ingredients: String,
    @SerializedName("image")
    val image: String
) {
    // otomatik primary key atanacak
    @PrimaryKey(autoGenerate = true)
    var uuid:  Int=0
}