package com.example.huertoandroid.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.huertoandroid.data.local.db.AppDatabase
import com.example.huertoandroid.data.repo.UserRepository

class LoginVmFactory(private val appContext: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = Room.databaseBuilder(appContext, AppDatabase::class.java, "huerto.db").build()
        val repo = UserRepository(db.userDao())
        @Suppress("UNCHECKED_CAST")
        return LoginViewModel(repo) as T
    }
}
