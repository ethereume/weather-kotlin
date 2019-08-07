package com.example.weather.presenter

import com.example.weather.model.WeatherService
import com.example.weather.router.Router
import com.example.weather.view.ForecastView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ForecastPresenter(private val httpWeatherService: WeatherService, private val router: Router) :
    MvpPresenter<ForecastView> {

    private var view: ForecastView? = null
    override val disposableBag = CompositeDisposable()


    init {
        observeView(router, ForecastView::class.java)
    }


    private fun bindView() {
        view?.let {

            val cityChanges = it.placeChanges().observeOn(AndroidSchedulers.mainThread())
            val forecastChanges = httpWeatherService.forecastChanges().observeOn(AndroidSchedulers.mainThread())
            val selectedFOrecast = it.selectedForecastCHanges().observeOn(AndroidSchedulers.mainThread())

            disposableBag.add(forecastChanges.subscribe { view?.addCity(it) })
            disposableBag.add(selectedFOrecast.subscribe {
                httpWeatherService.currentForecast.onNext(it)
                router.routesChanges.onNext(Router.Route.FORECAST_DETAILS)
            })
            disposableBag.add(cityChanges.subscribe(this::getForecastCity))
        }
    }

    private fun getForecastCity(city: String) {
        view?.showMessage("Refreshing weather")
        view?.toogleAvalibility(true)

        val subscription = httpWeatherService.getForecast(city)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view?.addCity(it)
                view?.toogleAvalibility(false)
                view?.clearTextInput()
            }
        disposableBag.add(subscription)
    }

    override fun detachView() {
        view = null
        disposableBag.clear()
    }

    override fun attachView(view: ForecastView) {
        this.view = view
        bindView()
    }
}