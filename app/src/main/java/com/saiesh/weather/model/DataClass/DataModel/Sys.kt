package com.saiesh.weather.model.DataClass.DataModel


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    var country: String,
    @SerializedName("sunrise")
    var sunrise: Int=0,
    @SerializedName("sunset")
    var sunset: Int=0
)