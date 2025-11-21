package com.example.huertoandroid.network

import com.example.huertoandroid.model.Product
import retrofit2.Response
import retrofit2.http.*
import com.example.huertoandroid.model.WeatherResponse


interface ApiService {

    // GET /api/products - Obtener todos los productos
    @GET("api/products")
    suspend fun getAllProducts(): Response<List<Product>>

    // GET /api/products/{id} - Obtener un producto por ID
    @GET("api/products/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<Product>

    // POST /api/products - Crear un nuevo producto
    @POST("api/products")
    suspend fun createProduct(@Body product: Product): Response<Product>

    // PUT /api/products/{id} - Actualizar un producto existente
    @PUT("api/products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body product: Product
    ): Response<Product>

    // DELETE /api/products/{id} - Eliminar un producto
    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<Void>

    // GET /api/products/search?nombre=X - Buscar productos por nombre
    @GET("api/products/search")
    suspend fun searchProducts(@Query("nombre") nombre: String): Response<List<Product>>

    // GET /api/products/category/{categoria} - Obtener productos por categoría
    @GET("api/products/category/{categoria}")
    suspend fun getProductsByCategory(@Path("categoria") categoria: String): Response<List<Product>>

    // GET /api/products/disp - Obtener solo productos con stock disponible
    @GET("api/products/disp")
    suspend fun getDispProducts(): Response<List<Product>>

    // PATCH /api/products/{id}/stock?stock=X - Actualizar solo el stock
    @PATCH("api/products/{id}/stock")
    suspend fun updateStock(
        @Path("id") id: Long,
        @Query("stock") stock: Int
    ): Response<Product>

    // API del Clima (OpenWeatherMap)
    @GET("https://api.openweathermap.org/data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String = "Santiago",
        @Query("appid") apiKey: String = "30f98f5543ab32d3c8780ea7fa6d4a75",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "es"
    ): Response<WeatherResponse>

}