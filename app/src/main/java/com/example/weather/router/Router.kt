package com.example.weather.router

import android.content.Context
import android.content.Intent
import com.example.weather.view.ForecastActivity
import com.example.weather.view.ForecastActivityDetail
import com.example.weather.view.MvpView
import io.reactivex.subjects.BehaviorSubject

class Router(private val context: Context) {

    val routesChanges = BehaviorSubject.create<Route>()
    val lifeCycleChanges = BehaviorSubject.create<LifeCycleEvent>()

    init {
        routesChanges.subscribe {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            when (it) {
                Route.FORECAST_DETAILS -> intent.setClass(context, ForecastActivityDetail::class.java)
                Route.FORECAST -> intent.setClass(context, ForecastActivity::class.java)
            }
            context.startActivity(intent)
        }
    }

    enum class LifeCycleEvent {
        CREATED, WILL_DESTROY;

        var view: MvpView? = null
            private set

        fun with(view: MvpView): LifeCycleEvent {
            this.view = view
            return this
        }
    }

    enum class Route {
        FORECAST_DETAILS, FORECAST
    }
}