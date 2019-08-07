package com.example.weather.model.storage

import com.example.weather.*
import com.example.weather.model.City
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class SqlLite(private val helper: DbHelper) : ForecastRepository {

    override fun save(city: City) = helper.use {
        deleteRows(CityTable.name, "${CityTable.Colums.id} = ${city.id}")
        deleteRows(ForecastTable.name, "${ForecastTable.Colums.CityId} = ${city.id}")
        val cityDb = city.toDbModel()
        insert(CityTable.name, *cityDb.map.toPairArray())
        cityDb.forecastList.forEach { insert(ForecastTable.name, *it.map.toPairArray()) }
    }


    override fun getCityByName(name: String): City? = helper.use {

        val cityDb = select(CityTable.name)
            .whereSimple("${CityTable.Colums.city} = $name")
            .parseOptional { CityDb(HashMap(it)) }

        cityDb?.let {
            it.forecastList = helper.use {
                select(ForecastTable.name)
                    .whereSimple("${ForecastTable.Colums.CityId} = ?", cityDb.city)
                    .parseList { ForecastDb(HashMap(it)) }
            }
        }
        cityDb?.toDomainModel()
    }

    override fun getLast(): City? = helper.use {
        val cityDb = select(CityTable.name)
            .orderBy(CityTable.Colums.timestamp, SqlOrderDirection.DESC)
            .limit(1)
            .parseOptional { CityDb(HashMap(it)) }

        cityDb?.let {
            it.forecastList = helper.use {
                select(ForecastTable.name)
                    .whereSimple("${ForecastTable.Colums.CityId} = ?", cityDb.city)
                    .parseList { ForecastDb(HashMap(it)) }
            }
        }
        cityDb?.toDomainModel()
    }

}