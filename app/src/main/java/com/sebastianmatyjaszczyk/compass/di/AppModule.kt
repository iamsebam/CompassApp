package com.sebastianmatyjaszczyk.compass.di

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import com.sebastianmatyjaszczyk.compass.model.AzimuthProvider
import com.sebastianmatyjaszczyk.compass.model.SensorListener
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun providesSensorManager(
        @ApplicationContext context: Context
    ): SensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager

    @Provides
    fun providesAzimuthProvider(
        sensorManager: SensorManager
    ): AzimuthProvider = SensorListener(sensorManager)
}