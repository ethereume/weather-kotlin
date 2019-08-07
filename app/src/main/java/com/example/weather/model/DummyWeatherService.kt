package com.example.weather.model

import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableJust
import io.reactivex.subjects.BehaviorSubject

class DummyWeatherService(override var currentForecast: BehaviorSubject<Forecast> = BehaviorSubject.create()) : WeatherService {
    override fun forecastChanges(): Observable<City> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getForecast(place: String) = ObservableJust(City(1, "DupCIty", "Poland", listOf()))
}