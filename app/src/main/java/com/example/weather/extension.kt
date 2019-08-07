package com.example.weather

import android.database.sqlite.SQLiteDatabase
import com.example.weather.model.City
import com.example.weather.model.Forecast
import com.example.weather.model.api.ForecastResultTo
import com.example.weather.model.api.ForecastTo
import com.example.weather.model.storage.CityDb
import com.example.weather.model.storage.ForecastDb
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import java.text.DateFormat

fun ForecastResultTo.toDomainModel() = City(city.id, city.name, city.country, list.map { it.toDomainModel() })

fun ForecastTo.toDomainModel() = Forecast(
    -1, dt * 1000, weather[0].description, temp.max.toInt(), temp.min.toInt(),
    "http://openweathermap.org/img/w/${weather[0].icon}.png"
)

fun SQLiteDatabase.deleteRows(table: String, where: String) {
    execSQL("delete from $table where $where")
}


fun City.toDbModel() = CityDb(id, name, country, forecastList.map { it.toDbModel(id) }, System.currentTimeMillis())

fun Forecast.toDbModel(cityId: Long) = ForecastDb(date, description, high, low, iconUrl, cityId)

fun CityDb.toDomainModel() = City(_id, city, country, forecastList.map { it.toDomainModel() })

fun ForecastDb.toDomainModel() = Forecast(_id, date, description, high, low, iconUrl)

fun Long.formatDate(dateFormat: Int = DateFormat.MEDIUM): String {
    return DateFormat.getDateInstance(dateFormat).format(this)
}

fun <K,V>MutableMap<K,V>.toPairArray():Array<Pair<K,V>> = map { Pair(it.key,it.value) }.toTypedArray()

fun <T : Any> SelectQueryBuilder.parseOptional(parser: (Map<String, Any?>) -> T): T? =
    parseOpt(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
    })
fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T): List<T> =
    parseList(object : MapRowParser<T> {
        override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
    })
