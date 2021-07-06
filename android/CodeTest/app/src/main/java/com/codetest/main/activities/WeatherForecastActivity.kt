package com.codetest.main.activities

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetest.R
import com.codetest.main.models.LocationModel
import com.codetest.main.repositories.LocationRepository
import com.codetest.main.ui.LocationViewHolder
import kotlinx.android.synthetic.main.activity_main.*


class WeatherForecastActivity : BaseActivity(contentView = R.layout.activity_main) {

    private var adapter = LocationAdapter()
    private var locations: List<LocationModel> = arrayListOf()

    private val locationRepo = LocationRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        setupAddLocationButton()
    }

    override fun onResume() {
        super.onResume()

        locationRepo
            .fetchLocations()
            .doOnSubscribe { showLoading() }
            .subscribe(
                {
                    locations = it
                    adapter.notifyDataSetChanged()
                    showContent()
                },
                ::showError
            )
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

    private inner class LocationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount() = locations.size
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LocationViewHolder.create(parent)
        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as? LocationViewHolder)?.setup(locations[position])
        }
    }
}