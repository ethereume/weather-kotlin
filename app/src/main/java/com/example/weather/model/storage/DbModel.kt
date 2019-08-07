package com.example.weather.model.storage

class CityDb(val map: MutableMap<String, Any?>, var forecastList: List<ForecastDb> = ArrayList()) {

    var _id: Long by map
    var city: String by map
    var country: String by map
    var timestamp: Long by map

    constructor(id: Long, city: String, country: String, forecastList: List<ForecastDb>, timestamp: Long) : this(HashMap(), forecastList) {
        this._id = id
        this.city = city
        this.country = country
        this.timestamp = timestamp
    }

}

class ForecastDb(val map: MutableMap<String, Any?>) {

    var _id: Long by map
    var date: Long by map
    var description: String by map
    var high: Int by map
    var low: Int by  map
    var iconUrl: String by map
    var cityId: Long by map

    constructor(date: Long, description: String, high: Int, low: Int, iconUrl: String, cityId: Long) : this(HashMap()) {
        this.date = date
        this.description = description
        this.high = high
        this.low = low
        this.iconUrl = iconUrl
        this.cityId = cityId
    }

}