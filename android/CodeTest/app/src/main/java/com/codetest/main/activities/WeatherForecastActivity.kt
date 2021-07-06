package com.codetest.main.activities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetest.R
import com.codetest.main.models.LocationModel
import com.codetest.main.repositories.LocationRepository
import com.codetest.main.ui.LocationViewHolder
import kotlinx.android.synthetic.main.activity_main.*


class WeatherForecastActivity : BaseLceActivity(contentView = R.layout.activity_main) {

    private var adapter = LocationAdapter()
    private var locations: List<LocationModel> = arrayListOf()

    private val locationRepo = LocationRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        setupAddLocationButton()
        setupTryAgainButton()
    }

    override fun onResume() {
        super.onResume()
        fetchLocations()
    }

    private fun setupAdapter() {
        adapter = LocationAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setupAddLocationButton() {
        btn_add_location.setOnClickListener {
            startActivity(AddLocationActivity.intent(this))
        }
    }

    private fun setupTryAgainButton() {
        setTryAgainOnClickListener {
            fetchLocations()
        }
    }

    private fun fetchLocations() {
        locationRepo
            .getLocations()
            .doOnSubscribe { showLoading() }
            .subscribe(
                { newLocations ->
                    locations = newLocations
                    adapter.notifyDataSetChanged()
                    showContent()
                },
                { throwable ->
                    showError()
                    showErrorDialog(throwable)
                }
            )
    }

    private fun deleteLocationAndUpdate(id: String) {
        locationRepo
            .deleteLocation(id)
            .doOnSubscribe { showLoading() }
            .subscribe(
                {
                    Toast.makeText(this, "Location deleted!", Toast.LENGTH_LONG).show()
                    fetchLocations()
                },
                { throwable ->
                    showContent()
                    showErrorDialog(throwable)
                }
            )
    }

    private inner class LocationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount() = locations.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationViewHolder.create(parent)
        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as? LocationViewHolder)?.setup(
                location = locations[position],
                onLongPress = { id -> deleteLocationAndUpdate(id) }
            )
        }
    }
}