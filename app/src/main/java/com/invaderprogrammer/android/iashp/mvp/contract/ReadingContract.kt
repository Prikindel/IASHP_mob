package com.invaderprogrammer.android.iashp.mvp.contract

import com.invaderprogrammer.android.iashp.rest.Reading

class ReadingContract {
    interface View : BaseContract.View {
        fun addReading(reading: Reading)
        fun showProgress()
        fun hideProgress()
        fun showErrorMessage(error: String?)
        fun refresh()
    }

    abstract class Presenter : BaseContract.Presenter<View>() {
        abstract fun makeReading()
        abstract fun refreshReading()
        abstract fun postReading(read: String, value: Int)
    }
}