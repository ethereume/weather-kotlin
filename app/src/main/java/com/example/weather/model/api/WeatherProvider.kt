package com.example.weather.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherProvider {

    @GET(value = "forecast/daily?cnt=10&units=metric&appid=b933866e6489f58987b2898c89f542b8")
    fun getForecast(@Query("q") place: String): Observable<ForecastResultTo>
}