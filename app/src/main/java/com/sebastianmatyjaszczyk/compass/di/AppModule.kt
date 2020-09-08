package com.sebastianmatyjaszczyk.compass.di

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
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
}