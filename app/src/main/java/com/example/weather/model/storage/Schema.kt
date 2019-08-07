package com.example.weather.model.storage

import org.jetbrains.anko.db.AUTOINCREMENT
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT

object CityTable {
    const val name = "city"

    val createColumns = arrayOf(
        Colums.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        Colums.city to TEXT,
        Colums.country to TEXT,
        Colums.timestamp to INTEGER
    )

    object Colums {
        const val id = "_id"
        const val city = "city"
        const val country = "country"
        const val timestamp = "timestamp"

    }
}

object ForecastTable {
    const val name = "forecast"

    val createColumns = arrayOf(
        Colums.id to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        Colums.description to TEXT,
        Colums.date to INTEGER,
        Colums.timestamp to INTEGER,
        Colums.low to INTEGER,
        Colums.hight to INTEGER
    )

    object Colums {
        const val id = "_id"
        const val date = "date"
        const val description = "description"
        const val timestamp = "timestamp"
        const val low = "low"
        const val hight = "hight"
        const val CityId = "cityId"
    }
}