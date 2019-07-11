package com.example.espl.sampleappkotlin.models

import android.databinding.BaseObservable
import android.databinding.ObservableField
import com.google.gson.annotations.SerializedName
import java.util.*
import android.R.attr.name
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.databinding.Bindable
import android.util.Log
import com.android.databinding.library.baseAdapters.BR
import android.widget.ImageView
import android.databinding.BindingAdapter
import com.bumptech.glide.Glide


class ImageData : BaseObservable() {

    @SerializedName("url")
    @get:Bindable
    var url: String? = null
        set(value) {
            field = value
            Log.e("ItemsSizee","::"+"calledSet")
            notifyPropertyChanged(BR.url)
        }

    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    @SerializedName("width")
    var width: String? = null

    @SerializedName("height")
    var height: String? = null

    @SerializedName("size")
    var size: String? = null

    @SerializedName("mp4")
    var mp4: String? = null

    @SerializedName("mp4_size")
    var mp4_size: String? = null

    @SerializedName("webp")
    var webp: String? = null

    @SerializedName("webp_size")
    var webp_size: String? = null
}
