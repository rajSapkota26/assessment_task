package com.example.assessment.feature.login.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assessment.service_layer.repository.RoomRepository

class LoginVMFactory(private var application: Application, private var repository: RoomRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginVM::class.java)){
            return LoginVM(application,repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}