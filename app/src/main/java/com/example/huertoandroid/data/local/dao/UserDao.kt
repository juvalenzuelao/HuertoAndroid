package com.example.huertoandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.huertoandroid.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) FROM user WHERE email = :email")
    suspend fun countByEmail(email: String): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity)

    // para login
    @Query("SELECT COUNT(*) FROM user WHERE email = :email AND passHash = :pass")
    suspend fun existsByEmailAndPass(email: String, pass: String): Int
}
