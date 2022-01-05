package com.geektech.weatherapp.ui.firstFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.geektech.weatherapp.App;
import com.geektech.weatherapp.common.Resource;
import com.geektech.weatherapp.data.models.Weather;
import com.geektech.weatherapp.data.repository.MainRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class FirstFragmentViewModel extends ViewModel {

    public LiveData<Resource<Weather>> liveData;
    private MainRepository repository;

    @Inject
    public FirstFragmentViewModel(MainRepository repository) {
        this.repository = repository;
    }

    public void getWeather() {
        liveData = repository.getWeather();
    }

    public void getWeatherByCity(String cityName){
        liveData = repository.getWeatherByCity(cityName);
    }
}
