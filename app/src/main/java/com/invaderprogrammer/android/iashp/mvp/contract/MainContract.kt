package com.invaderprogrammer.android.iashp.mvp.contract

import android.widget.Spinner
import com.invaderprogrammer.android.iashp.rest.Zavod

class MainContract {
    interface View : BaseContract.View {
        fun addZavod(zavod: Zavod)
        fun showProgress()
        fun hideProgress()
        fun showErrorMessage(error: String?)
        fun refresh()
        fun spinner(): Spinner
        fun loadFragment()
    }

    abstract class Presenter : BaseContract.Presenter<View>() {
        abstract fun makeSpiner()
        abstract fun refreshSpiner()
        abstract fun clickSpiner()
        abstract fun getCountItemTest(): Int
    }
}