package com.sebastianmatyjaszczyk.compass.model

data class OrientationAnglesEntity(
    val azimuth: Int,
    val pitch: Int,
    val roll: Int
) : Comparable<OrientationAnglesEntity> {

    override fun compareTo(other: OrientationAnglesEntity): Int = this.azimuth - other.azimuth

}