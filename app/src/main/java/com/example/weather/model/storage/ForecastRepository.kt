package com.example.weather.model.storage

import com.example.weather.model.City

interface ForecastRepository {

    fun save(city: City)

    fun getCityByName(name: String): City?

    fun getLast(): City?

}