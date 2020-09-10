package com.sebastianmatyjaszczyk.compass.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

// TODO maybe create and implement an interface here, in case using another provider like rotating vector matrix
class OrientationAnglesProvider @Inject constructor(
    private val sensorManager: SensorManager
) : SensorEventListener {

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    val orientationAnglesSubject: BehaviorSubject<OrientationAnglesEntity> = BehaviorSubject.createDefault(OrientationAnglesEntity(0, 0, 0))

    init {
        registerListener()
    }

    private fun registerListener() {
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD).also { magneticFieldSensor ->
            sensorManager.registerListener(this, magneticFieldSensor, SensorManager.SENSOR_DELAY_UI, SensorManager.SENSOR_DELAY_UI)
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER).also { accelerometerSensor ->
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_UI, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun unregisterListener() {
        sensorManager.unregisterListener(this)
    }

    // TODO: Extract calculations to another class
    private fun calculateAzimuth(): Int {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)
        val azimuth = SensorManager.getOrientation(rotationMatrix, orientationAngles)[0]
        val degrees = (Math.toDegrees(azimuth.toDouble()) + 360) % 360
        return degrees.toInt()
    }

    private fun calculatePitch(): Int {
        val pitch = orientationAngles[1]
        val degrees = Math.toDegrees(pitch.toDouble()) % 180
        return degrees.toInt()
    }

    private fun calculateRoll(): Int {
        val roll = orientationAngles[2]
        val degrees = Math.toDegrees(roll.toDouble()) % 90
        return degrees.toInt()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            when (sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> System.arraycopy(values, 0, accelerometerReading, 0, accelerometerReading.size)
                Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(values, 0, magnetometerReading, 0, magnetometerReading.size)
            }
        } ?: return
        orientationAnglesSubject.onNext(
            OrientationAnglesEntity(
                calculateAzimuth(),
                calculatePitch(),
                calculateRoll()
            )
        )
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // NO-OP
    }

}
