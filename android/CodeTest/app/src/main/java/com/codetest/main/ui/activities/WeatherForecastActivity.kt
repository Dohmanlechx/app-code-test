package com.codetest.main.ui.activities

import android.app.AlertDialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetest.R
import com.codetest.main.models.LocationModel
import com.codetest.main.ui.LocationViewHolder
import com.codetest.main.util.showToast
import com.codetest.main.viewmodels.LocationViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class WeatherForecastActivity : BaseLceActivity(contentView = R.layout.activity_main) {
    private val viewModel: LocationViewModel by viewModel()
    private val locations: List<LocationModel>
        get() = viewModel.cachedLocations()

    private var adapter = LocationAdapter()

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
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
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
            .showLoading()
            .subscribe(
                { populateViews() },
                ::showErrorButtonAndDialog
            )
    }

    private fun populateViews() {
        adapter.notifyDataSetChanged()
        showContent()
    }

    private fun showErrorButtonAndDialog(throwable: Throwable) {
        showError()
        showErrorDialog(throwable)
    }

    private fun deleteLocationAndUpdate(location: LocationModel) {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.delete_location_title))
            .setMessage(resources.getString(R.string.delete_location_body, location.name))
            .setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                viewModel
                    .deleteLocation(location.id)
                    .showLoading()
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
            (viewHolder as? LocationViewHolder)?.bind(
                location = locations[position],
                onLongPress = ::deleteLocationAndUpdate
            )
        }
    }
}