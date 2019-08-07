package com.example.weather.presenter

import com.example.weather.model.WeatherService
import com.example.weather.router.Router
import com.example.weather.view.ForecastDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ForecastDetailPresenter(private val weatherService: WeatherService,private val router:Router) : MvpPresenter<ForecastDetailView> {

    private var view: ForecastDetailView? = null
    override val disposableBag = CompositeDisposable()

    init {
        observeView(router,ForecastDetailView::class.java)
    }

    override fun attachView(view: ForecastDetailView) {
        this.view = view
        bindView()
    }

    private fun bindView() {
        view?.let {
            val selected = weatherService.currentForecast.observeOn(AndroidSchedulers.mainThread())
            disposableBag.add(selected.subscribe() {view?.showForecast(it)})
        }
    }

    override fun detachView() {
        view = null
        disposableBag.clear()
    }
}