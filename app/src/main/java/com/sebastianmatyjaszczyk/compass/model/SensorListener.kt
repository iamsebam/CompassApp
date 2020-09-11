package com.sebastianmatyjaszczyk.compass.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class SensorListener @Inject constructor(
    private val sensorManager: SensorManager
) : SensorEventListener, AzimuthProvider {

    override val azimuth: BehaviorSubject<Float> = BehaviorSubject.createDefault(0f)

    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

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

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            when (sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> System.arraycopy(
                    lowPassFilter(values, accelerometerReading),
                    0,
                    accelerometerReading,
                    0,
                    accelerometerReading.size
                )
                Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(
                    lowPassFilter(values, magnetometerReading),
                    0,
                    magnetometerReading,
                    0,
                    magnetometerReading.size
                )
            }
        } ?: return
        azimuth.onNext(calculateAzimuth())
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // NO-OP
    }

    private fun calculateAzimuth(): Float {
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerReading, magnetometerReading)
        val azimuth = SensorManager.getOrientation(rotationMatrix, orientationAngles)[0]
        return ((Math.toDegrees(azimuth.toDouble()) + 360) % 360).toFloat()
    }

    private fun lowPassFilter(input: FloatArray, output: FloatArray): FloatArray {
        if (output.isEmpty()) return input
        val alpha = 0.95f
        for (i in output.indices) {
            output[i] = alpha * output[i] + (1 - alpha) * input[i]
        }
        return output
    }
}
