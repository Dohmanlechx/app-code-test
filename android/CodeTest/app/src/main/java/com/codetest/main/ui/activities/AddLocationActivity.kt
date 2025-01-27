package com.codetest.main.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import com.codetest.R
import com.codetest.main.api.models.LocationRequest
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import com.codetest.main.util.listeners.EditTextListener
import com.codetest.main.util.listeners.WeatherSpinnerListener
import com.codetest.main.util.showToast
import com.codetest.main.viewmodels.LocationViewModel
import kotlinx.android.synthetic.main.activity_add_location.*
import org.koin.android.viewmodel.ext.android.viewModel

class AddLocationActivity : BaseLceActivity(contentView = R.layout.activity_add_location) {
    companion object {
        fun intent(context: Context): Intent =
            Intent(context, AddLocationActivity::class.java)
    }

    private val viewModel: LocationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupValidators()
        setupStatusSpinner()
        setupSubmitButton()
    }

    private fun setupValidators() {
        et_name.addTextChangedListener(object : EditTextListener() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateInputs()
            }
        })
        et_temperature.addTextChangedListener(object : EditTextListener() {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validateInputs()
            }
        })
    }

    private fun setupStatusSpinner() {
        val statusArray =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, WeatherStatus.formattedNames)

        spinner_weather_status.apply {
            adapter = statusArray
            onItemSelectedListener = object : WeatherSpinnerListener(
                onSelected = ::setSelectedWeather,
                onNothingSelected = ::setSelectedWeather
            ) {}
        }
    }

    private fun setSelectedWeather(weatherStatus: WeatherStatus) {
        viewModel.selectedWeatherStatus = weatherStatus
        validateInputs()
    }

    private fun validateInputs() {
        btn_confirm_location.isEnabled =
            et_name.text?.isNotEmpty() == true
                    && et_temperature.text?.isNotEmpty() == true
                    && viewModel.selectedWeatherStatus != WeatherStatus.NOT_SET
    }

    private fun setupSubmitButton() {
        btn_confirm_location.setOnClickListener {
            viewModel
                .postLocation(
                    LocationRequest(
                        name = et_name.text.toString(),
                        temperature = et_temperature.text.toString().toInt(),
                        status = viewModel.selectedWeatherStatus.name
                    )
                )
                .showLoading()
                .subscribe(
                    { newLocation ->
                        exitActivity(newLocation)
                    },
                    { throwable ->
                        showContent()
                        showErrorDialog(throwable)
                    }
                )
        }
    }

    private fun exitActivity(location: LocationModel) {
        this.showToast("${location.name} added!")
        finish()
    }
}