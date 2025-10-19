package com.example.huertoandroid.data.repo

import com.example.huertoandroid.data.local.dao.UserDao
import com.example.huertoandroid.data.local.entity.UserEntity

class UserRepository(private val dao: UserDao) {
    suspend fun isEmailAvailable(email: String) = dao.countByEmail(email) == 0
    suspend fun register(nombre: String, email: String, pass: String) =
        dao.insert(UserEntity(nombre = nombre, email = email, passHash = pass))
    suspend fun login(email: String, pass: String) =
        dao.existsByEmailAndPass(email, pass) > 0
}
