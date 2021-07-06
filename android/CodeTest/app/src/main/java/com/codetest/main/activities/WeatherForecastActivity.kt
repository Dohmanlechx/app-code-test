package com.codetest.main.activities

import android.app.AlertDialog
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

    private var adapter = ListAdapter()
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
        adapter = ListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun setupAddLocationButton() {
        btn_add_location.setOnClickListener {
            startActivity(AddLocationActivity.intent(this))
        }
    }

    private fun postLocation() {
//        locationRepo.postLocation().subscribe({
//        }, {
//            Toast.makeText(CodeTestApplication.context, "Failed to add location", Toast.LENGTH_SHORT).show()
//        })
    }

    private inner class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun getItemCount(): Int {
            return locations.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return LocationViewHolder.create(parent)
        }

        override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
            (viewHolder as? LocationViewHolder)?.setup(locations[position])
        }
    }
}