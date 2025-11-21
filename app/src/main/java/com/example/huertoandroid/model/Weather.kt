package com.example.huertoandroid.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<WeatherDescription>
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)

data class WeatherDescription(
    val description: String,
    val icon: String
)