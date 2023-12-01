package com.arodmar432p.blackjackprueba3.vistaModelo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JuegoViewModel::class.java)) {
            return JuegoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}