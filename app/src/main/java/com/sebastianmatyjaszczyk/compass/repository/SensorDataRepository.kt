package com.sebastianmatyjaszczyk.compass.repository

import com.sebastianmatyjaszczyk.compass.model.OrientationAnglesEntity
import com.sebastianmatyjaszczyk.compass.model.OrientationAnglesProvider
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class SensorDataRepository @Inject constructor(
    private val orientationAnglesProvider: OrientationAnglesProvider
) {
    val orientationAngles: Flowable<OrientationAnglesEntity> = orientationAnglesProvider.orientationAnglesSubject
        .toFlowable(BackpressureStrategy.LATEST)
        .buffer(BUFFER_SIZE)
        .map(::toMedian)
}

private fun toMedian(list: List<OrientationAnglesEntity>) = list.sorted()[list.middleIndex()]
private fun List<OrientationAnglesEntity>.middleIndex() = (this.size / 2) - 1

private const val BUFFER_SIZE = 3