package com.example.weather.model.api


data class WeatherTo(val id: Long, val main: String, val description: String, val icon: String)

data class TemperatureTo(
    val day: Float,
    val min: Float,
    val max: Float,
    val night: Float,
    val eve: Float,
    val morn: Float
)

data class ForecastTo(
    val dt: Long, val temp: TemperatureTo, val pressure: Float, val humidity: Int,
    val weather: List<WeatherTo>, val speed: Float, val deg: Int, val clouds: Int, val rain: Float
)

data class CoordinatesTo(val lon: Float, val lat: Float)

data class CityTo(val id: Long, val name: String, val coord: CoordinatesTo, val country: String)

data class ForecastResultTo(val city: CityTo, val list: List<ForecastTo>)
