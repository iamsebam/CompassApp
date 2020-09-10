package com.sebastianmatyjaszczyk.compass.view.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sebastianmatyjaszczyk.compass.repository.SensorDataRepository

class MainViewModel
@ViewModelInject constructor(
    private val sensorDataRepository: SensorDataRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {
    val orientationAnglesLiveData = LiveDataReactiveStreams.fromPublisher(sensorDataRepository.orientationAngles)

}