package com.example.weather.config

import android.content.Context
import com.example.weather.model.WeatherService
import com.example.weather.presenter.ForecastDetailPresenter
import com.example.weather.presenter.ForecastPresenter
import com.example.weather.router.Router
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class PresenterModul {

    @Provides
    @Singleton
    fun router(context: Context) = Router(context)

    @Provides
    fun forecastDetailPresenter(@Named(value = "http") httpWeatherService: WeatherService, router: Router) =
        ForecastDetailPresenter(httpWeatherService, router)

    @Provides
    fun forecastPresenter(@Named(value = "http") httpWeatherService: WeatherService, router: Router) =
        ForecastPresenter(httpWeatherService, router)
}