package com.sebastianmatyjaszczyk.compass.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sebastianmatyjaszczyk.compass.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.azimuthLiveData.observe(this, ::updateView)
    }

    private fun updateView(azimuth: Float) {
        compassView.azimuth = azimuth
        azimuthTextView.text = String.format(azimuth.toInt().toString() + getString(R.string.degree))
    }
}