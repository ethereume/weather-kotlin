package com.example.weather.model

import android.util.Log
import com.example.weather.model.api.WeatherProvider
import com.example.weather.model.storage.ForecastRepository
import com.example.weather.toDomainModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class HttpWeatherService(private val weatherApi: WeatherProvider, private val forecast: ForecastRepository,
                         override var currentForecast: BehaviorSubject<Forecast> = BehaviorSubject.create()
) : WeatherService {

    private val disposableBlag = CompositeDisposable()
    private val forecastChanges = BehaviorSubject.create<City>()

    init {
        forecast.getLast()?.let {
            forecastChanges.onNext(it)
        }
    }

    override fun getForecast(place: String): Observable<City> {

        forecast.getCityByName(place)?.let { forecastChanges.onNext(it) }

        val subscribeOn = weatherApi.getForecast(place)
            .subscribeOn(Schedulers.io())
            .map { it.toDomainModel() }
            .share()
        // .subscribe({forecast.save(it)})
        disposableBlag.add(subscribeOn.subscribe({ onForecastRefreshed(it) }, { Log.d("XXX", "Refresh failed") }))
        return subscribeOn
    }

    private fun onForecastRefreshed(city: City) {
        forecastChanges.onNext(city)
        forecast.save(city)
    }

    override fun forecastChanges(): Observable<City> = forecastChanges
}