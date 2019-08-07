package com.example.weather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.ForecastApplication
import com.example.weather.R
import com.example.weather.adapter.WeatherAdapter
import com.example.weather.model.City
import com.example.weather.model.Forecast
import com.example.weather.router.Router
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_forecast.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_forecast.forecastRecyclerView as recyclerView

class ForecastActivity : AppCompatActivity(), ForecastView {

    @Inject
    lateinit var router: Router

    override fun selectedForecastCHanges(): Observable<Forecast> = selectedForecastSubject

    override fun toogleAvalibility(avability: Boolean) {
        searchEditText.isEnabled = avability
    }

    override fun clearTextInput() {
        searchEditText.text.clear()
    }

    private val selectedForecastSubject = BehaviorSubject.create<Forecast>()

    override fun showMessage(text: String) {
        toast(text)
    }

    override fun placeChanges(): Observable<String> = searchEditText.textChanges()
        .map { it.toString() }
        .filter { it.length > 3 }
        .debounce(500, TimeUnit.MILLISECONDS)

    override fun addCity(city: City) {
        title = city.name
        recyclerView.adapter = WeatherAdapter(city.forecastList, selectedForecastSubject::onNext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)
        ForecastApplication.graph.inject(this)
        router.lifeCycleChanges.onNext(Router.LifeCycleEvent.CREATED.with(this))
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        router.lifeCycleChanges.onNext(Router.LifeCycleEvent.WILL_DESTROY.with(this))
    }
}
