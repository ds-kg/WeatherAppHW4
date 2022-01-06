package com.geektech.weatherapp.ui.firstFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.geektech.weatherapp.R;
import com.geektech.weatherapp.common.Resource;
import com.geektech.weatherapp.data.models.Clouds;
import com.geektech.weatherapp.data.models.Main;
import com.geektech.weatherapp.data.models.Sys;
import com.geektech.weatherapp.data.models.Weather;
import com.geektech.weatherapp.data.models.Weather__1;
import com.geektech.weatherapp.data.models.Wind;
import com.geektech.weatherapp.databinding.FragmentFirstBinding;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FirstFragment extends Fragment {

    private NavController controller;
    private FirstFragmentArgs args;

    private FragmentFirstBinding binding;
    private FirstFragmentViewModel viewModel;
    private Main main;
    private Sys sys;
    private Weather weather;
    private ArrayList<Weather__1> weather__1 = new ArrayList<>();
    private Wind wind;

    public FirstFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(FirstFragmentViewModel.class);
        args = FirstFragmentArgs.fromBundle(getArguments());
        viewModel.setCity(args.getCity());
        viewModel.getWeatherByCity();
        NavHostFragment hostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager()
                .findFragmentById(R.id.navHost);
        controller = hostFragment.getNavController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.liveData.observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case SUCCESS:
                    main = resource.data.getMain();
                    sys = resource.data.getSys();
                    weather = resource.data;
                    weather__1 = (ArrayList<Weather__1>) resource.data.getWeather();
                    wind = resource.data.getWind();
                    setWeather();
                    binding.progress.setVisibility(View.GONE);
                    break;
                case ERROR:
                    Toast.makeText(requireContext(), "Error :(", Toast.LENGTH_SHORT).show();
                    binding.progress.setVisibility(View.GONE);
                    break;
                case LOADING:
                    binding.progress.setVisibility(View.VISIBLE);
                    break;
            }
        });

        binding.tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.action_firstFragment_to_secondFragment);
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void setWeather() {
        Glide.with(requireContext()).load("https://openweathermap.org/img/wn/" + weather__1.get(0).getIcon() + ".png")
                .into(binding.ivCloud);
        binding.weatherNowTv.setText(weather__1.get(0).getMain());
        binding.firstGradusTv.setText(String.valueOf((int) Math.round(main.getTempMax())));
        binding.secondGradusTv.setText(String.valueOf((int) Math.round(main.getTempMin())));
        binding.windMainTv.setText((int) Math.round(wind.getSpeed()) + "m/s");
        binding.tvLocation.setText(weather.getName());
        binding.mainTempTv.setText(String.valueOf((int) Math.round(main.getTemp())));
        binding.pressureMainTv.setText(main.getPressure() + "mBar");
        binding.humidityTv.setText(main.getHumidity() + "%");
    }
}