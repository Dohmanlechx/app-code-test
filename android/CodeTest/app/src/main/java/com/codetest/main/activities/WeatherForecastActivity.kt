package com.codetest.main.activities

import android.app.AlertDialog
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

    private fun deleteLocationAndUpdate(location: LocationModel) {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.delete_location_title))
            .setMessage(resources.getString(R.string.delete_location_body, location.name))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                locationRepo
                    .deleteLocation(location.id)
                    .doOnSubscribe { showLoading() }
                    .subscribe(
                        {
                            Toast.makeText(this, "${location.name} deleted!", Toast.LENGTH_LONG).show()
                            fetchLocations()
                        },
                        { throwable ->
                            showErrorDialog(throwable)
                            fetchLocations()
                        }
                    )
            }
            .create()
            .show()
    }

    private inner class LocationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount() = locations.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationViewHolder.create(parent)
        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as? LocationViewHolder)?.setup(
                location = locations[position],
                onLongPress = { location -> deleteLocationAndUpdate(location) }
            )
        }
    }
}