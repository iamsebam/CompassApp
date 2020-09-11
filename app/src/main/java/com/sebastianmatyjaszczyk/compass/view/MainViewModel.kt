package com.sebastianmatyjaszczyk.compass.view

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sebastianmatyjaszczyk.compass.repository.SensorDataRepository

class MainViewModel
@ViewModelInject constructor(
    sensorDataRepository: SensorDataRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    val azimuthLiveData = LiveDataReactiveStreams.fromPublisher(sensorDataRepository.orientationAngles)

}