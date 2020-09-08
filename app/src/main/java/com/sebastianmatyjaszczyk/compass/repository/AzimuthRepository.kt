package com.sebastianmatyjaszczyk.compass.repository

import com.sebastianmatyjaszczyk.compass.model.SensorDataProvider
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class AzimuthRepository @Inject constructor(
    private val sensorDataProvider: SensorDataProvider
) {
    val azimuth: Flowable<Int> = sensorDataProvider.azimuthSubject
        .toFlowable(BackpressureStrategy.BUFFER)
        .buffer(5)
        .map { buffer ->
            buffer.reduce { a, b ->
                a + b
            } / buffer.size
        }
}