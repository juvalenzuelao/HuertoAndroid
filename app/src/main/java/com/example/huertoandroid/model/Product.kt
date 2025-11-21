package com.example.huertoandroid.model

data class Product(
    val id: Long? = null,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val cantidadKg: Int,
    val categoria: String,
    val imagenUrl: String? = null
)