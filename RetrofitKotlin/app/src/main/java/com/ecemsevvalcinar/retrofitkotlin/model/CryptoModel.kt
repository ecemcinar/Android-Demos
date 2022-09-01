package com.ecemsevvalcinar.retrofitkotlin.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
   // @SerializedName("currency")
    var currency: String,
   // @SerializedName("price")
    var price: String)

//Kotlinde degisken isimleri, JSON formatindaki dosyada cekigemiz verilerin isimleriyle ayni ise,
// SerializedName kismini yazmaya gerek yok