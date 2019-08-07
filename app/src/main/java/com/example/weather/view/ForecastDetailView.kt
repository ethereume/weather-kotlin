package com.example.weather.view

import com.example.weather.model.Forecast

interface ForecastDetailView : MvpView {

    fun showForecast(forecast: Forecast)
}