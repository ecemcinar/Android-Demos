package com.ecemsevvalcinar.foodapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ecemsevvalcinar.foodapp.R

fun placeholderProgressBar(context: Context): CircularProgressDrawable {

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

fun ImageView.downloadFromUrl(url: String?, circularProgressDrawable: CircularProgressDrawable){

    // image inerken ne gosterilecek belirlerken -> placeholder
    val options = RequestOptions()
        .placeholder(circularProgressDrawable)
        .error(R.mipmap.ic_launcher_round) // hata varsa default resim gosterilecek

    Glide.with(context)
        .setDefaultRequestOptions(options) // hata varsa default resim gosterilecek
        .load(url)
        .into(this)
}

@BindingAdapter("android:downloadUrl") // xml'de bu fonksiyonu calistirmamak icin
fun downloadImage(view: ImageView, url:String?){
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}