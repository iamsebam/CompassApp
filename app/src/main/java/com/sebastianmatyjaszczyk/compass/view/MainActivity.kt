package com.sebastianmatyjaszczyk.compass.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sebastianmatyjaszczyk.compass.R
import com.sebastianmatyjaszczyk.compass.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.azimuthLiveData.observe(this, ::displayAzimuth)
    }

    private fun displayAzimuth(azimuth: Int) {
        azimuthTextView.text = azimuth.toString()
    }
}