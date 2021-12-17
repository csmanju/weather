package com.saiesh.weather.model.DataClass.DataModel


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var all: Int
)