package com.example.weather.presentation.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.FragmentMainBinding
import com.example.weather.presentation.Params
import com.example.weather.presentation.WeatherListAdapter
import com.example.weather.presentation.viewModels.MainViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null

    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var myAdapter: WeatherListAdapter

    //Запрос разрешений
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.isNotEmpty() && map.values.all { it }) {
                getData()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInfoIsLoading()
        setupRecyclerView()
        checkListState()
        checkPermission()
    }

    //Очитска binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Проверка отображение загрузки
    private fun checkInfoIsLoading() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoIsLoading.collect {
                    if (it) {
                        binding.rvWeather.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.rvWeather.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    //Проверка на разрешения
    private fun checkPermission() {
        if (REQUIRED_PERMISSIONS.all
            { permission ->
                ContextCompat.checkSelfPermission(this.requireContext(), permission) ==
                        PackageManager.PERMISSION_GRANTED
            }
        ) getData()
        else launcher.launch(REQUIRED_PERMISSIONS)
    }

    //Проверка данных
    private fun checkListState() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherMeasurements.collect {
                    myAdapter.submitList(it)
                }
            }
        }
    }

    //Настройка RecyclerView
    private fun setupRecyclerView() {
        with(binding.rvWeather) {
            myAdapter = WeatherListAdapter { date -> goToDetailFragment(date) }
            adapter = myAdapter
        }
    }

    private fun getData() {
        //Обязательная проверка на разрешения
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUIRED_CODE
            )
            return
        }
        //Получение локации в данный момент
        LocationServices
            .getFusedLocationProviderClient(requireActivity())
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false

            }).addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    viewModel.getFiveDaysWeather(lat, lon)
                } else Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.location_error),
                    Toast.LENGTH_SHORT
                ).show()
            }


    }

    //Переход на Detail Fragment
    private fun goToDetailFragment(date: String) {
        val bundle = Bundle().apply {
            putString(Params.PARAM_FOR_DETAIL, date)
        }//Упаковка данных
        findNavController().navigate(R.id.action_navigation_main_to_detailFragment, bundle)//Переход с данными

    }

    //Kонстанты
    companion object {
        private val REQUIRED_PERMISSIONS: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        private const val REQUIRED_CODE = 1

    }

}