package com.example.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Forecast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*

class WeatherAdapter(private val weather: List<Forecast>, private val itemSelectedListener: (Forecast) -> Unit = {}) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = weather.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindView(weather[position])

    inner class ViewHolder(val eventName: View) : RecyclerView.ViewHolder(eventName) {
        fun bindView(forecast: Forecast) = with(forecast) {
            Picasso.get().load(iconUrl).into(eventName.forecastIcon)
            eventName.forecastDate.text = date.toString()
            eventName.forecastDescription.text = description
            eventName.forecastMaxTemp.text = high.toString()
            eventName.forecastMinTemp.text = low.toString()
            eventName.setOnClickListener { itemSelectedListener(forecast) }
        }
    }
}