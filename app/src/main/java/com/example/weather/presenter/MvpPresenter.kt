package com.example.weather.presenter

import com.example.weather.router.Router
import com.example.weather.view.MvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

interface MvpPresenter<V : MvpView> {

    val disposableBag: CompositeDisposable

    fun observeView(router: Router, viewType: Class<out MvpView>) {
        disposableBag.add(router.lifeCycleChanges.observeOn(AndroidSchedulers.mainThread())
            .filter { viewType.isInstance(it.view) }
            .filter { it == Router.LifeCycleEvent.CREATED }
            .map { it.view as V }
            .subscribe {
                attachView(it)
            })

        disposableBag.add(router.lifeCycleChanges.observeOn(AndroidSchedulers.mainThread())
            .filter { viewType.isInstance(it) }
            .filter { it == Router.LifeCycleEvent.WILL_DESTROY }
            .map { it.view as V }
            .subscribe {
                detachView()
            })
    }

    fun attachView(view: V) {}

    fun detachView() {}

}