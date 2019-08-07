package com.example.weather.config

import com.example.weather.ForecastApplication
import com.example.weather.view.ForecastActivity
import com.example.weather.view.ForecastActivityDetail
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ModelModul::class, PresenterModul::class, EagerModule::class])
@Singleton
interface Graph {

    fun inject(forecastView: ForecastActivity)

    fun inject(forecastView: ForecastActivityDetail)

    fun eager(forecastApplication: ForecastApplication)

}