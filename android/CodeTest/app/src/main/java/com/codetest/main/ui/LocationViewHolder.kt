package com.codetest.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codetest.R
import com.codetest.main.models.LocationModel
import com.codetest.main.models.WeatherStatus
import com.codetest.main.util.weatherInfo
import kotlinx.android.synthetic.main.location.view.*


class LocationViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup): LocationViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.location, parent, false)
            return LocationViewHolder(view)
        }
    }

    fun bind(location: LocationModel, onLongPress: (LocationModel) -> Unit) {
        itemView.apply {
            name.text = location.name
            weatherInfo.text = location.weatherInfo()
            card.setCardBackgroundColor(getColor(location.status))
            card.setOnLongClickListener {
                onLongPress(location)
                true
            }
        }
    }

    private fun getColor(status: WeatherStatus): Int {
        return when (status) {
            WeatherStatus.SUNNY,
            WeatherStatus.MOSTLY_SUNNY,
            WeatherStatus.PARTLY_SUNNY,
            WeatherStatus.PARTLY_SUNNY_RAIN,
            WeatherStatus.BARELY_SUNNY -> ContextCompat.getColor(itemView.context, R.color.blue)
            WeatherStatus.THUNDER_CLOUD_AND_RAIN,
            WeatherStatus.TORNADO,
            WeatherStatus.LIGHTENING -> ContextCompat.getColor(itemView.context, R.color.red)
            WeatherStatus.CLOUDY,
            WeatherStatus.SNOW_CLOUD,
            WeatherStatus.RAINY -> ContextCompat.getColor(itemView.context, R.color.grey)
            WeatherStatus.NOT_SET -> ContextCompat.getColor(itemView.context, R.color.white)
        }
    }
}
