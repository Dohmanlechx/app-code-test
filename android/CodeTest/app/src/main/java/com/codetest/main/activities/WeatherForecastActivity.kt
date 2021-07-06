package com.codetest.main.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codetest.R
import com.codetest.main.extensions.showToast
import com.codetest.main.models.LocationModel
import com.codetest.main.repositories.LocationRepository
import com.codetest.main.ui.LocationViewHolder
import com.codetest.main.viewmodels.LocationViewModel
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_main.*


class WeatherForecastActivity : BaseLceActivity(contentView = R.layout.activity_main) {

    private var adapter = LocationAdapter()
    private var locations: List<LocationModel> = arrayListOf()

    private val viewModel = LocationViewModel(LocationRepository())

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
        viewModel
            .getLocations()
            .doOnSubscribe { showLoading() }
            .autoDisposable(scope(Lifecycle.Event.ON_DESTROY))
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
                viewModel
                    .deleteLocation(location.id)
                    .doOnSubscribe { showLoading() }
                    .autoDisposable(scope(Lifecycle.Event.ON_DESTROY))
                    .subscribe(
                        {
                            this.showToast("${location.name} deleted!")
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