package com.example.weather.model

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface WeatherService {
    fun getForecast(place: String): Observable<City>

    fun forecastChanges() : Observable<City>

    var currentForecast : BehaviorSubject<Forecast>
}