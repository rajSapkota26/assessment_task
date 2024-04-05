package com.example.assessment.feature.register.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assessment.service_layer.repository.RoomRepository

class RegisterVMFactory(private var application: Application, private var repository: RoomRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterVM::class.java)){
            return RegisterVM(application,repository) as T
        }
       throw IllegalArgumentException("Unknown viewmodel class")
    }
}