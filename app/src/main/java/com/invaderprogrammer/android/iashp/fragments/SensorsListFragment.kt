package com.invaderprogrammer.android.iashp.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.invaderprogrammer.android.iashp.R
import com.invaderprogrammer.android.iashp.adapter.BaseAdapter
import com.invaderprogrammer.android.iashp.adapter.SensorsAdapter
import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.mvp.contract.SensorsContract
import com.invaderprogrammer.android.iashp.mvp.presenter.SensorsPresenter
import com.invaderprogrammer.android.iashp.rest.Sensor
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class SensorsListFragment : BaseListFragment(R.layout.fragment_sensors_list), SensorsContract.View {

    @Inject
    lateinit var presenter: SensorsPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        presenter.attach(this)
        presenter.makeList()
    }

    override fun createAdapterInstance(): BaseAdapter<*> {
        return SensorsAdapter()
    }

    override fun addSensor(sensor: Sensor) {
        if (sensor.name != "0") {
            viewAdapter.add(sensor)
        }
    }

    override fun notifyAdapter() {
        viewAdapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        requireActivity().progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        requireActivity().progress.visibility = View.INVISIBLE
    }

    override fun showErrorMessage(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun refresh() {
        viewAdapter.items.clear()
        viewAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }


}
