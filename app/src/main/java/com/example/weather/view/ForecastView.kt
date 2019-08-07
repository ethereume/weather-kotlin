package com.example.weather.view

import com.example.weather.model.City
import com.example.weather.model.Forecast
import io.reactivex.Observable

interface ForecastView : MvpView {

    fun showMessage(text: String)

    fun addCity(city: City)

    fun placeChanges(): Observable<String>

    fun clearTextInput()

    fun toogleAvalibility(avability: Boolean)

    fun selectedForecastCHanges(): Observable<Forecast>

}