package com.example.weather.config

import android.content.Context
import com.example.weather.ForecastApplication
import com.example.weather.model.DummyWeatherService
import com.example.weather.model.HttpWeatherService
import com.example.weather.model.WeatherService
import com.example.weather.model.api.WeatherProvider
import com.example.weather.model.storage.DbHelper
import com.example.weather.model.storage.ForecastRepository
import com.example.weather.model.storage.SqlLite
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ModelModul(private val applicationContext: ForecastApplication) {

    @Singleton
    @Provides // @Provides like bean
    fun httpClient(): OkHttpClient {
        var logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient().newBuilder().addInterceptor(logger).build()
    }

    @Provides
    @Singleton
    fun retrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun context(): Context = applicationContext

    @Provides
    @Singleton
    fun dbHelper(context: Context): DbHelper = DbHelper(context)

    @Provides
    @Singleton
    fun databaseForecastRepository(database: DbHelper): ForecastRepository = SqlLite(database)

    @Provides
    @Singleton
    fun weatherApi(retrofit: Retrofit) = retrofit.create(WeatherProvider::class.java)

    @Named(value = "http")
    @Provides
    @Singleton
    fun weatherService(weatherApi: WeatherProvider, forecastRepository: ForecastRepository): WeatherService =
        HttpWeatherService(weatherApi, forecastRepository)

    @Named(value = "dummy")
    @Provides
    @Singleton
    fun dummyWeatherService(weatherApi: WeatherProvider): WeatherService = DummyWeatherService()
}