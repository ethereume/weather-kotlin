package com.example.weather.config

import com.example.weather.presenter.ForecastDetailPresenter
import com.example.weather.presenter.ForecastPresenter
import dagger.Module
import dagger.Provides

@Module
class EagerModule {

    @Provides
    fun eager(forecastPresenter: ForecastPresenter, forecastPresenterDetail: ForecastDetailPresenter) = ""

}