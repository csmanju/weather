package com.saiesh.weather.Network
import com.saiesh.weather.model.DataClass.DataModel.WeatherRes
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {



    @Headers("Accept:application/json")
    @GET("weather")
     fun callApiForWeatherInfo(@Query("q") name: String, @Query("appid") id:String): Call<WeatherRes>

}