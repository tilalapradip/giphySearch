package com.example.espl.sampleappkotlin.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.BindingAdapter
import android.util.Log
import android.view.View
import com.example.espl.sampleappkotlin.BR

class CategoryResponse : BaseObservable() {

    @get:Bindable
    var data: ArrayList<DataItem> = arrayListOf()
        set(value) {
            field = value
            Log.e("ItemsSizee","::Setcalled")
            notifyPropertyChanged(BR.viewModel)
        }

    @get:Bindable
    var isVisible: Boolean=true
        set(value) {
            field = value
            Log.e("ItemsSizee","::Setcalled")
            notifyPropertyChanged(BR.viewModel)
        }


    @get:Bindable
    var isLoading: Boolean=false
        set(value) {
            field = value
            Log.e("ItemsSizee","::Setcalled")
            notifyPropertyChanged(BR.viewModel)
        }

    @get:Bindable
    var islastPage: Boolean=false
        set(value) {
            field = value
            Log.e("ItemsSizee","::Setcalled")
            notifyPropertyChanged(BR.viewModel)
        }

    @get:Bindable
    var isVisibleProgress: Boolean=false
        set(value) {
            field = value
            Log.e("ItemsSizee","::Setcalled")
            notifyPropertyChanged(BR.viewModel)
        }

    companion object {
        @JvmStatic
        @BindingAdapter("visibleOrGone")
        fun View.visibleOrGone(visible: Boolean) {
            visibility = if (visible) View.VISIBLE else View.GONE
        }
    }
}

class DataItem: BaseObservable() {
     var type: String? = null
     var  id: String? = null
     var slug: String? = null
     var url: String? = null
     var bitly_gif_url: String? = null
     var bitly_url: String? = null
     var embed_url:String? = null
     var username: String? = null
     var source: String? = null
     var rating: String? = null
     var content_url: String? = null
     var source_tld:String? = null
     var source_post_url: String? = null
     var is_sticker: String? = null
     var import_datetime: String? = null
     var trending_datetime:String? = null
     var images:HashMap<String, ImageData>? = null
}