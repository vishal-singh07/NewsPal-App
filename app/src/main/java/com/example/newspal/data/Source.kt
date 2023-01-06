package com.example.newspal.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    var id   : String? = null,
    var name : String? = null
) : Parcelable
