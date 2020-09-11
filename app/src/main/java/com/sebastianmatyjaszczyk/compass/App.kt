package com.sebastianmatyjaszczyk.compass

import android.app.Activity
import android.app.Application
import com.sebastianmatyjaszczyk.compass.model.SensorListener
import com.sebastianmatyjaszczyk.compass.util.ActivityCallbacks
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), ActivityCallbacks {

    @Inject
    lateinit var sensorListener: SensorListener

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity) {
        sensorListener.unregisterListener()
        super.onActivityPaused(activity)
    }
}