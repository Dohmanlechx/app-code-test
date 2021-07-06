package com.codetest.main.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.codetest.R
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import com.codetest.main.repositories.LocationRepository
import kotlinx.android.synthetic.main.activity_add_location.*

class AddLocationActivity : BaseActivity(contentView = R.layout.activity_add_location) {
    companion object {
        fun intent(context: Context): Intent =
            Intent(context, AddLocationActivity::class.java)
    }

    private val locationRepo = LocationRepository()
    private var selectedWeatherStatus = WeatherStatus.UNKNOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStatusSpinner()
        setupSubmitButton()
    }

    private fun setupStatusSpinner() {
        val statusArray =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, WeatherStatus.formattedNames)

        spinner_weather_status.apply {
            adapter = statusArray
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedWeatherStatus = WeatherStatus.values()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }
    }

    private fun setupSubmitButton() {
        btn_confirm_location.setOnClickListener {
            // TODO: Add validations
            val location =
                LocationRequest(
                    name = et_name.text.toString(),
                    temperature = et_temperature.text.toString(),
                    status = selectedWeatherStatus.name
                )

            locationRepo
                .postLocation(location)
                .doOnSubscribe { showLoading() }
                .subscribe(
                    ::startExitAnimation,
                    ::showError
                )
        }
    }

    private fun startExitAnimation(location: LocationModel) {
        // TODO: Add animation
        Toast.makeText(this, "${location.name} added!", Toast.LENGTH_LONG).show()
        finish()
    }
}