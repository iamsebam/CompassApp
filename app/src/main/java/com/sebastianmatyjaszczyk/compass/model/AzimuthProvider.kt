package com.sebastianmatyjaszczyk.compass.model

import io.reactivex.rxjava3.subjects.BehaviorSubject

interface AzimuthProvider {
    val azimuth: BehaviorSubject<Float>
}