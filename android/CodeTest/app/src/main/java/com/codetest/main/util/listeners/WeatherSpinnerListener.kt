package com.codetest.main.util.listeners

import android.view.View
import android.widget.AdapterView
import com.codetest.main.models.WeatherStatus

abstract class WeatherSpinnerListener(
    val onSelected: (WeatherStatus) -> Unit,
    val onNothingSelected: (WeatherStatus) -> Unit
) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
        onSelected(WeatherStatus.values()[position])

    override fun onNothingSelected(parent: AdapterView<*>?) =
        onNothingSelected(WeatherStatus.NOT_SET)
}