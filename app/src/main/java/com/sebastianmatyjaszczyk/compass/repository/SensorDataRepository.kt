package com.sebastianmatyjaszczyk.compass.repository

import com.sebastianmatyjaszczyk.compass.model.AzimuthProvider
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class SensorDataRepository @Inject constructor(
    azimuthProvider: AzimuthProvider
) {

    val orientationAngles: Flowable<Float> = azimuthProvider.azimuth.toFlowable(BackpressureStrategy.LATEST)

}