package com.example.espl.sampleappkotlin

import android.app.Application
import com.example.espl.sampleappkotlin.objectbox.ObjectBox

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this);
    }
}