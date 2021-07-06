package com.codetest.main

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.codetest.R
import com.codetest.main.api.LocationApiService
import com.codetest.main.model.Location
import com.codetest.main.ui.LocationViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList


class WeatherForecastActivity : AppCompatActivity() {

    private var adapter = ListAdapter()
    private var locations: List<Location> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codetest.R.layout.activity_main)

        adapter = ListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        fetchLocations()
    }

    private fun fetchLocations() {
        getLocations { response ->
            if (response == null) {
                showError()
            } else {
                locations = response
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showError() {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.error_title))
            .setMessage(resources.getString(R.string.error_title))
            .setPositiveButton(resources.getString(R.string.ok), { _, _ -> })
            .create()
            .show()
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

// TODO: Remove
fun getLocations(callback: (List<Location>?) -> Unit) {
    val locations: ArrayList<Location> = arrayListOf()
    val apiKey = KeyUtil().getKey()
    LocationApiService.getApi().get(apiKey, "locations", {
        val list = it.get("locations").asJsonArray
        for (json in list) {
            locations.add(Location.from(json.asJsonObject))
        }
        callback(locations)
    }, {
        callback(null)
    })
}