package com.example.espl.sampleappkotlin.objectbox

import android.content.Context
import com.example.espl.sampleappkotlin.BuildConfig

import io.objectbox.BoxStore


object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext).build()
    }
}