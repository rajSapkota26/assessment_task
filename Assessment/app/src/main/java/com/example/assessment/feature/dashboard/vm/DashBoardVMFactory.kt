package com.example.assessment.feature.dashboard.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assessment.service_layer.repository.RoomRepository

class DashBoardVMFactory(
    private var application: Application,
    private var repository: RoomRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashBoardVM::class.java)) {
            return DashBoardVM(application, repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }

}