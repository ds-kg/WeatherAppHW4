package com.geektech.weatherapp.data.remote;

import com.geektech.weatherapp.data.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("weather?")
    Call<Weather> getTemp(
            @Query("q") String city,
            @Query("appid") String appId,
            @Query("units") String metric
    );
}
