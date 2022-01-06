package com.geektech.weatherapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.geektech.weatherapp.App;
import com.geektech.weatherapp.common.Resource;
import com.geektech.weatherapp.data.models.Weather;
import com.geektech.weatherapp.data.remote.WeatherApi;
import com.geektech.weatherapp.ui.firstFragment.FirstFragmentArgs;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {

    private WeatherApi api;
    private String city;

    public void setCity(String city) {
        this.city = city;
    }

    @Inject
    public MainRepository(WeatherApi api) {
        this.api = api;
    }

    public MutableLiveData<Resource<Weather>> getWeather() {

        MutableLiveData<Resource<Weather>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Resource.loading());
        api.getTemp(city, "82ed191a02db835cc3b61d5910def7b0", "metric").enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mutableLiveData.setValue(Resource.success(response.body()));
                } else {
                    mutableLiveData.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                mutableLiveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return mutableLiveData;
    }
}
