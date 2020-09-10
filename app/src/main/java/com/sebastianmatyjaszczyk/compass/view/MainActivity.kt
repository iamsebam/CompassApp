package com.sebastianmatyjaszczyk.compass.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sebastianmatyjaszczyk.compass.R
import com.sebastianmatyjaszczyk.compass.model.OrientationAnglesEntity
import com.sebastianmatyjaszczyk.compass.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.orientationAnglesLiveData.observe(this, ::displayCompass)
    }

    private fun displayCompass(orientationAngles: OrientationAnglesEntity) {
        compassView.animate().apply {
            rotation(-orientationAngles.azimuth.toFloat())
            rotationX(-orientationAngles.pitch.toFloat() / 2.5f)
            rotationY(-orientationAngles.roll.toFloat() / 2.5f)
        }
        azimuthTextView.text = orientationAngles.azimuth.toString()
    }
}