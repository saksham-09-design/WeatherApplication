package com.example.weather_application

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface ApiInterFace {
    @GET("weather")
    fun getWeatherData(
        @Query("q") city: String,
        @Query("appid") appid: String,
        @Query("units") units: String
    ):Call<WeatherApp>
}