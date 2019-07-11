package com.example.espl.sampleappkotlin.objectbox

import android.databinding.BaseObservable
import android.databinding.Bindable
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class UserLikes(
    @Id var id: Long=0,
    var userId: String,
    var like: Int?,
    var dislike: Int?

)