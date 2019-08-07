package com.example.weather

import android.app.Application
import com.example.weather.config.Graph
import com.example.weather.config.DaggerGraph
import com.example.weather.config.ModelModul
import javax.inject.Inject

class ForecastApplication : Application() {

    @Inject
    lateinit var eager: String

    companion object {
        lateinit var graph: Graph
        private set
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerGraph.builder().modelModul(ModelModul(this)).build()
        graph.eager(this)
    }

}