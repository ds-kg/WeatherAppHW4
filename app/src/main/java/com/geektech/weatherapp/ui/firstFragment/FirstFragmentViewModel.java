package com.geektech.weatherapp.ui.firstFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.geektech.weatherapp.App;
import com.geektech.weatherapp.common.Resource;
import com.geektech.weatherapp.data.models.Weather;
import com.geektech.weatherapp.data.repository.MainRepository;

public class FirstFragmentViewModel extends ViewModel {

    public LiveData<Resource<Weather>> liveData;

    public FirstFragmentViewModel() {
    }

    public void getWeather() {
        liveData = App.repository.getWeather();
    }
}
