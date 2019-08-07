package com.example.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weather.ForecastApplication
import com.example.weather.R
import com.example.weather.model.Forecast
import com.example.weather.router.Router
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class ForecastActivityDetail : AppCompatActivity(), ForecastDetailView {

    override fun showForecast(forecast: Forecast) {
        test.text = forecast.description
    }

    @Inject
    lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ForecastApplication.graph.inject(this)
        router.lifeCycleChanges.onNext(Router.LifeCycleEvent.CREATED.with(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        router.lifeCycleChanges.onNext(Router.LifeCycleEvent.WILL_DESTROY.with(this))
    }
}