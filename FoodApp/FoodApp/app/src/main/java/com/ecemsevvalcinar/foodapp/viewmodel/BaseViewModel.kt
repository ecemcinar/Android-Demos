package com.ecemsevvalcinar.foodapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract  class BaseViewModel(application: Application): AndroidViewModel(application), CoroutineScope {
    // coroutinelerle calismak icin bu class olusturuldu

    // androidviewmodel application parametresi ister
    // application ile calismak daha iyidir, belirli bi activity ya da fragment ile calisirken destroy oldugunda
    // probleme yol acabilir

    private val job = Job()

    // "arka planda islemi yapip mainthreade don"
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    // app destory edildigince ne olacak -> jobi cancella
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}