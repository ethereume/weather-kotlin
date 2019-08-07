package com.example.weather.model

data class City(val id: Long, val name: String, val country: String, val forecastList: List<Forecast>)

data class Forecast(val id: Long, val date: Long, val description: String, val high: Int, val low: Int, val iconUrl: String)