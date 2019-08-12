package com.invaderprogrammer.android.iashp.mvp.contract

import com.invaderprogrammer.android.iashp.rest.Sensor

class SensorsContract {
    interface View : BaseContract.View {
        fun addSensor(sensor: Sensor)
        fun notifyAdapter()
        fun showProgress()
        fun hideProgress()
        fun showErrorMessage(error: String?)
        fun refresh()
    }

    abstract class Presenter : BaseContract.Presenter<View>() {
        abstract fun makeList()
        abstract fun refreshList()
    }
}