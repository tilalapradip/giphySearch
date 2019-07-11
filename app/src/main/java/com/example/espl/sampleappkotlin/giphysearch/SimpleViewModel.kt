package com.example.espl.sampleappkotlin.giphysearch

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.espl.sampleappkotlin.models.CategoryResponse
import com.example.espl.sampleappkotlin.retrofitPacked.ApiEndpoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SimpleViewModel : ViewModel(), CoroutineScope {
    var catRes: CategoryResponse =
        CategoryResponse()

    val redditApi = ApiEndpoint.create()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

     fun getDataItemsPage(query: String, itemoffset: Int) {

         CoroutineScope(coroutineContext).launch {
            try {
                catRes.data.addAll(redditApi.getPeople(query,itemoffset).body()!!.data)
                catRes.isVisible=false
                catRes.isLoading=false
                catRes.isVisibleProgress=false
                if(catRes.islastPage==false && catRes.data.size==0){
                    catRes.islastPage=true
                    catRes.isVisible=true
                }
                catRes.notifyChange()
            } catch (exception: Exception) {
                Log.e("ItemsSizee", exception.message)
            }
        }
    }

}