package com.example.weather_application

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.roundToInt
import android.widget.SearchView
import com.example.weather_application.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// API Key: 6effbaa6591766a3487565912539cf91

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherData("Abohar")
        searchCity()
        about()
    }
    private fun about(){
        val abt = binding.about
        abt.setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }
    }
    private fun searchCity(){
        val searchView = binding.locationSearch
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
    private fun fetchWeatherData(cityName: String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterFace::class.java)
        val response = retrofit.getWeatherData(cityName,"c7872fdbeab2af54a5536e598f805435", "metric")
        response.enqueue(object: Callback<WeatherApp> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val ResponseBody = response.body()
                if(response.isSuccessful && ResponseBody != null){
                    val temprature = (ResponseBody.main.temp-3).roundToInt().toString()
                    val humidity = ResponseBody.main.humidity
                    val windspeed = ResponseBody.wind.speed
                    val sunrise = ResponseBody.sys.sunrise.toLong()
                    val sunset = ResponseBody.sys.sunset.toLong()
                    val conditions = ResponseBody.weather.firstOrNull()?.main?: "unknown"
                    val sealevel = ResponseBody.main.sea_level
                    binding.temprature.text = "$temprature°C"
                    binding.conditions.text = conditions
                    binding.sunset.text = time(sunset)
                    binding.surrise.text = time(sunrise)
                    binding.humedity.text = "$humidity %"
                    binding.windSpeed.text = "$windspeed m/s"
                    binding.conditionBelow.text = conditions
                    binding.seaLevel.text = "$sealevel hPa"
                    binding.location.text = cityName
                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = dateToday(System.currentTimeMillis())
                    changeBackground(conditions)
                }
            }
            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
            }
        })
    }
    @SuppressLint("ResourceAsColor")
    private fun changeBackground(conditions: String) {
        val hour = getCurrentHour(System.currentTimeMillis()).toInt()
        when (conditions) {
            "Clear Sky", "Sunny", "Clear" -> {
                if((hour in 18..23)||(hour in 0..5)){
                    binding.root.setBackgroundResource(R.drawable.clearnight)
                    binding.lottieAnimationView.setAnimation(R.raw.nightclear)
                    binding.dayconst.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.conditionBelow.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.temprature.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.day.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.date.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.location.setTextColor(Color.parseColor("#FFFFFF"))
                    binding.saksham.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    binding.root.setBackgroundResource(R.drawable.sunnyday)
                    binding.lottieAnimationView.setAnimation(R.raw.sunny)
                    binding.dayconst.setTextColor(Color.parseColor("#000000"))
                    binding.conditionBelow.setTextColor(Color.parseColor("#000000"))
                    binding.temprature.setTextColor(Color.parseColor("#000000"))
                    binding.day.setTextColor(Color.parseColor("#000000"))
                    binding.date.setTextColor(Color.parseColor("#000000"))
                    binding.location.setTextColor(Color.parseColor("#000000"))
                    binding.saksham.setTextColor(Color.parseColor("#000000"))
                }
            }
            "Partly Clouds", "Clouds", "Overcast", "Mist", "Fog", "Haze", "Smoke" -> {
                if((hour in 18..23)||(hour in 0..5)){
                    binding.root.setBackgroundResource(R.drawable.cloud)
                    binding.lottieAnimationView.setAnimation(R.raw.nightcloud)
                } else {
                    binding.root.setBackgroundResource(R.drawable.cloud)
                    binding.lottieAnimationView.setAnimation(R.raw.dayclouds)
                }
                binding.dayconst.setTextColor(Color.parseColor("#000000"))
                binding.conditionBelow.setTextColor(Color.parseColor("#000000"))
                binding.temprature.setTextColor(Color.parseColor("#000000"))
                binding.day.setTextColor(Color.parseColor("#000000"))
                binding.date.setTextColor(Color.parseColor("#000000"))
                binding.location.setTextColor(Color.parseColor("#000000"))
                binding.saksham.setTextColor(Color.parseColor("#000000"))
            }
            "Light Rain", "Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain", "Thunderstorm" -> {
                if((hour in 18..23)||(hour in 0..5)){
                    binding.root.setBackgroundResource(R.drawable.rainbackground)
                    binding.lottieAnimationView.setAnimation(R.raw.nightrain)
                } else {
                    binding.root.setBackgroundResource(R.drawable.rainbackground)
                    binding.lottieAnimationView.setAnimation(R.raw.dayrain)
                }
                binding.dayconst.setTextColor(Color.parseColor("#000000"))
                binding.conditionBelow.setTextColor(Color.parseColor("#000000"))
                binding.temprature.setTextColor(Color.parseColor("#000000"))
                binding.day.setTextColor(Color.parseColor("#000000"))
                binding.date.setTextColor(Color.parseColor("#000000"))
                binding.location.setTextColor(Color.parseColor("#000000"))
                binding.saksham.setTextColor(Color.parseColor("#000000"))
            }
            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzard" -> {
                if((hour in 18..23)||(hour in 0..5)){
                    binding.root.setBackgroundResource(R.drawable.snowbackground)
                    binding.lottieAnimationView.setAnimation(R.raw.nightsnow)
                } else {
                    binding.root.setBackgroundResource(R.drawable.snowbackground)
                    binding.lottieAnimationView.setAnimation(R.raw.daysnow)
                }
                binding.dayconst.setTextColor(Color.parseColor("#000000"))
                binding.conditionBelow.setTextColor(Color.parseColor("#000000"))
                binding.temprature.setTextColor(Color.parseColor("#000000"))
                binding.day.setTextColor(Color.parseColor("#000000"))
                binding.date.setTextColor(Color.parseColor("#000000"))
                binding.saksham.setTextColor(Color.parseColor("#000000"))
                binding.location.setTextColor(Color.parseColor("#000000"))
            }
            else -> {
                if((hour in 18..23)||(hour in 0..5)){
                    binding.root.setBackgroundResource(R.drawable.clearnight)
                    binding.lottieAnimationView.setAnimation(R.raw.nightclear)
                } else {
                    binding.root.setBackgroundResource(R.drawable.sunnyday)
                    binding.lottieAnimationView.setAnimation(R.raw.sunny)
                }
                binding.dayconst.setTextColor(Color.parseColor("#000000"))
                binding.conditionBelow.setTextColor(Color.parseColor("#000000"))
                binding.temprature.setTextColor(Color.parseColor("#000000"))
                binding.day.setTextColor(Color.parseColor("#000000"))
                binding.date.setTextColor(Color.parseColor("#000000"))
                binding.saksham.setTextColor(Color.parseColor("#000000"))
                binding.location.setTextColor(Color.parseColor("#000000"))
            }
        }
        binding.lottieAnimationView.playAnimation()
    }
    private fun getCurrentHour(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH", Locale.getDefault())
        return sdf.format((Date()))
    }
    private fun dayName(timestamp: Long): String{
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
    private fun time(timestamp: Long): String{
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))
    }
    private fun dateToday(timestamp: Long): String{
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }
}