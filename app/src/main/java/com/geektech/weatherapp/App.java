package com.geektech.weatherapp;

import android.app.Application;

import com.geektech.weatherapp.data.remote.RetrofitClient;
import com.geektech.weatherapp.data.remote.WeatherApi;
import com.geektech.weatherapp.data.repository.MainRepository;

public class App extends Application {

    public static WeatherApi api;
    public static MainRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitClient client = new RetrofitClient();
        api = client.provideWeather();
        repository = new MainRepository();
    }
}
