package com.invaderprogrammer.android.iashp.fragments

import android.os.Bundle
import android.os.SystemClock.uptimeMillis
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.invaderprogrammer.android.iashp.R
import com.invaderprogrammer.android.iashp.di.App
import com.invaderprogrammer.android.iashp.mvp.contract.ReadingContract
import com.invaderprogrammer.android.iashp.mvp.presenter.ReadingPresenter
import com.invaderprogrammer.android.iashp.rest.Reading
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_reading_sensor.*
import javax.inject.Inject

class ReadingSensorFragment : Fragment(), ReadingContract.View {

    @Inject
    lateinit var presenter: ReadingPresenter

    private val sendInterval = 300L
    private var nextSend: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_sensor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appComponent.inject(this)
        presenter.attach(this)
        presenter.makeReading()

        // Обработка скролинга ползунка
        electricity.setOnSeekBarChangeListener(seekListener(electricity_value))
        water.setOnSeekBarChangeListener(seekListener(water_value))
        heat.setOnSeekBarChangeListener(seekListener(heat_value))
        vape.setOnSeekBarChangeListener(seekListener(vape_value))
        gas.setOnSeekBarChangeListener(seekListener(gas_value))
        spec_gas.setOnSeekBarChangeListener(seekListener(spec_gas_value))
    }

    private fun seekListener(view_value: TextView): SeekBar.OnSeekBarChangeListener {
        fun whenSeek(seekBar: SeekBar): String {
            return when (seekBar) {
                electricity -> "electricity"
                water -> "water"
                heat -> "heat"
                vape -> "vape"
                gas -> "gas"
                spec_gas -> "spec_gas"
                else -> ""
            }
        }

        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                view_value.text = i.toString()
                if (nextSend < uptimeMillis()) {
                    presenter.postReading(whenSeek(seekBar), i)
                    nextSend = uptimeMillis() + sendInterval
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                nextSend = 0
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                presenter.postReading(whenSeek(seekBar), seekBar.progress)
            }
        }
    }

    override fun addReading(reading: Reading) {
        electricity_value.text = reading.electricity.toString()
        water_value.text = reading.water.toString()
        heat_value.text = reading.heat.toString()
        vape_value.text = reading.vape.toString()
        gas_value.text = reading.gas.toString()
        spec_gas_value.text = reading.specGas.toString()

        electricity.progress = reading.electricity
        water.progress = reading.water
        heat.progress = reading.heat
        vape.progress = reading.vape
        gas.progress = reading.gas
        spec_gas.progress = reading.specGas
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
