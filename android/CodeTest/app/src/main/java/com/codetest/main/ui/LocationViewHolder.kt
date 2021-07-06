package com.codetest.main.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codetest.R
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import kotlinx.android.synthetic.main.location.view.*


class LocationViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.location, parent, false)
            return LocationViewHolder(view)
        }
    }

    fun setup(location: LocationModel) {
        itemView.card.setCardBackgroundColor(getColor(location.status))
        itemView.name.text = location.name
        val weather = location.temperature + "°C " + String(Character.toChars(location.status.value))
        itemView.weatherInfo.text = weather
    }

    private fun getColor(status: WeatherStatus): Int {
        return when (status) {
            WeatherStatus.SUNNY,
            WeatherStatus.MOSTLY_SUNNY,
            WeatherStatus.PARTLY_SUNNY,
            WeatherStatus.PARTLY_SUNNY_RAIN,
            WeatherStatus.BARELY_SUNNY -> itemView.resources.getColor(R.color.blue)
            WeatherStatus.THUNDER_CLOUD_AND_RAIN,
            WeatherStatus.TORNADO,
            WeatherStatus.LIGHTENING -> itemView.resources.getColor(R.color.red)
            WeatherStatus.CLOUDY,
            WeatherStatus.SNOW_CLOUD,
            WeatherStatus.RAINY -> itemView.resources.getColor(R.color.grey)
            WeatherStatus.UNKNOWN -> itemView.resources.getColor(R.color.white)
        }
    }
}
