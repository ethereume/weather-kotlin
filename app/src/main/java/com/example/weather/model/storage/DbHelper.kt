package com.example.weather.model.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(database: SQLiteDatabase?) {
        database?.createTable(CityTable.name, true, *CityTable.createColumns)
        database?.createTable(ForecastTable.name, true, *ForecastTable.createColumns)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {
        database?.dropTable(CityTable.name, true)
        database?.dropTable(ForecastTable.name, true)
    }

    companion object {
        const val dbName = "forecast.db"
        const val version = 1
    }
}